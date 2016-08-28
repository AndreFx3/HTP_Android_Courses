package andrewpolvoko.htp_android_courses;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private int currentTrackTime;
    private int fullTrackTime;
    private boolean isMusicPlaying = false;
    final Messenger mMessenger = new Messenger(new MessagesHandler());
    private Messenger mMusicService = null;
    private boolean isMusicServiceConnected = false;
    private SeekBar seekBar;
    private ImageView toggleButton;
    private TextView currentTrackTimeView;
    private TextView fullTrackTimeVIew;
    private ServiceConnection mServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentTrackTimeView = (TextView) findViewById(R.id.currentTrackTime);
        fullTrackTimeVIew = (TextView) findViewById(R.id.fullTrackTime);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);
        toggleButton = (ImageView) findViewById(R.id.ToggleButton);
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mMusicService = new Messenger(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mMusicService = null;
            }
        };
        bindService();

        if (savedInstanceState != null) {
            isMusicPlaying = savedInstanceState.getBoolean("isMusicPlaying");
            if (isMusicPlaying) toggleButton.setImageResource(R.drawable.ic_pause_black_24dp);
            seekBar.setMax(savedInstanceState.getInt("seekBarMax"));
            seekBar.setProgress(savedInstanceState.getInt("seekBarProgress"));
            currentTrackTime = savedInstanceState.getInt("currentTrackTime");
            fullTrackTime = savedInstanceState.getInt("fullTrackTime");
            currentTrackTimeView.setText(formatTime(currentTrackTime));
            fullTrackTimeVIew.setText(formatTime(fullTrackTime));
        } else {
            startService(new Intent(this, MyMusicService.class));
        }
        autoUpdaterSeekBar();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            sendMessageToService(Constants.SEEK, progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    class MessagesHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.SET_TRACK_DURATION:
                    seekBar.setMax(msg.arg1);
                    fullTrackTime = msg.arg1;
                    fullTrackTimeVIew.setText(formatTime(fullTrackTime));
                    break;
                case Constants.UPDATE_TRACK_TIME:
                    seekBar.setProgress(msg.arg1);
                    currentTrackTime = msg.arg1;
                    currentTrackTimeView.setText(formatTime(currentTrackTime));
                    break;
                case Constants.END_OF_TRACK:
                    seekBar.setProgress(0);
                    isMusicPlaying = false;
                    currentTrackTime = 0;
                    fullTrackTime = 0;
                    currentTrackTimeView.setText("00:00");
                    fullTrackTimeVIew.setText("00:00");
                    toggleButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    public String formatTime(int time) {
        long seconds = (time / 1000) % 60;
        long minutes = (time / (1000 * 60)) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public void sendMessageToService(int code) {
        Message msg = Message.obtain(null, code);
        msg.replyTo = mMessenger;
        try {
            mMusicService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToService(int code, int extras) {
        Message msg = Message.obtain(null, code);
        msg.replyTo = mMessenger;
        msg.arg1 = extras;
        try {
            mMusicService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void ToggleAudio(View view) {
        if (isMusicPlaying) {
            isMusicPlaying = false;
            toggleButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        } else {
            isMusicPlaying = true;
            toggleButton.setImageResource(R.drawable.ic_pause_black_24dp);
        }
        if (seekBar.getProgress() != currentTrackTime) {
            sendMessageToService(Constants.SEEK, seekBar.getProgress());
            sendMessageToService(Constants.TOGGLE_PLAYBACK);
        } else
            sendMessageToService(Constants.TOGGLE_PLAYBACK);
    }

    public void autoUpdaterSeekBar() {
        Thread updateActivityThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (isMusicPlaying) {
                            sendMessageToService(Constants.GET_TRACK_TIME);
                        }
                        Thread.sleep(200);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        updateActivityThread.start();

    }

    void bindService() {
        bindService(new Intent(this,
                MyMusicService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
        isMusicServiceConnected = true;
    }

    void unbindService() {
        if (isMusicServiceConnected) {
            unbindService(mServiceConnection);
            isMusicServiceConnected = false;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seekBarProgress", seekBar.getProgress());
        savedInstanceState.putInt("seekBarMax", seekBar.getMax());
        savedInstanceState.putInt("currentTrackTime", currentTrackTime);
        savedInstanceState.putInt("fullTrackTime", fullTrackTime);
        savedInstanceState.putBoolean("isMusicPlaying", isMusicPlaying);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        unbindService();
        super.onDestroy();
    }
}
