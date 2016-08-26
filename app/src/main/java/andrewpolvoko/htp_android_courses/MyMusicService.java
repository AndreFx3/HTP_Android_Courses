package andrewpolvoko.htp_android_courses;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class MyMusicService extends Service implements
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnInfoListener {

    private final String MUSIC_THREAD_NAME = "Music playback";
    final Messenger mMessenger = new Messenger(new MessagesHandler());
    private Messenger mActivity = null;
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    private MediaPlayer mMediaPlayer;


    @Override
    public void onCreate() {
        mHandlerThread = new HandlerThread(MUSIC_THREAD_NAME, Thread.MAX_PRIORITY);
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());

        mMediaPlayer = MediaPlayer.create(this, R.raw.one);

        /*mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mMediaPlayer.setDataSource(Constants.TrackURL);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    class MessagesHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.TOGGLE_PLAYBACK: {
                    mActivity = msg.replyTo;
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.pause();
                    } else {
                        sendMessageToActivity(Constants.INIT_SEEK_BAR, mMediaPlayer.getDuration());
                        mMediaPlayer.start();
                        autoUpdaterSeekBar();
                    }
                    break;
                }
                default:
                    super.handleMessage(msg);
            }
        }
    }

    public void sendMessageToActivity(int code, int a) {
        Message msg = Message.obtain(null, code);
        msg.replyTo = mMessenger;
        msg.arg1 = a;
        try {
            mActivity.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void autoUpdaterSeekBar() {

        Message msg = Message.obtain(null, Constants.UPDATE_BAR);
        msg.replyTo = mMessenger;

        Thread updateActivity = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (mMediaPlayer.isPlaying()) {
                        sendMessageToActivity(Constants.UPDATE_BAR, mMediaPlayer.getCurrentPosition());
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                }
            }
        });


    }

    @Override
    public void onDestroy() {
        mHandlerThread.quitSafely();
        mHandlerThread = null;
        mMediaPlayer.release();
        mMediaPlayer = null;
        super.onDestroy();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }
}
