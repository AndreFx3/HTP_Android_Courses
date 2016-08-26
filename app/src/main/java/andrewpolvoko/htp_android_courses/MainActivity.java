package andrewpolvoko.htp_android_courses;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    final Messenger mMessenger = new Messenger(new MessagesHandler());
    private Messenger mMusicService = null;
    private boolean isMusicServiceConnected = false;
    private SeekBar seekBar;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicService = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMusicService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        bindService();
    }

    class MessagesHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.INIT_SEEK_BAR:
                    seekBar.setMax(msg.arg1);
                    break;
                case Constants.UPDATE_BAR:
                    seekBar.setProgress(msg.arg1);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    public void ToggleAudio(View view){
        Message msg = Message.obtain(null, Constants.TOGGLE_PLAYBACK);
        msg.replyTo = mMessenger;
        try {
            mMusicService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
    protected void onDestroy() {
        unbindService();
        super.onDestroy();
    }
}
