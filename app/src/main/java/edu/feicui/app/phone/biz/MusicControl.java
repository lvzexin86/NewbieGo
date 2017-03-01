package edu.feicui.app.phone.biz;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import edu.feicui.app.phone.entity.Mp3Info;

/**
 * Created by 似水流年 on 2017/1/12.
 *
 * @author Nicholas.Lv
 */
public class MusicControl extends MediaPlayer {
    private static MusicControl instance = null;
    private Context mCon;
    private List<Mp3Info> mMp3InfoList;
    private Timer mTimer;
    private Handler mHandler;
    private final int WHAT = 1;
    private int mLength;
    private int mPosition;
    private static boolean stop;
    private int mMusicNumber;

    private MusicControl(Context con) {
        this.mCon = con;
        mTimer = new Timer();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case WHAT:
                        double progress = msg.getData().getDouble("progress");
                        calPlayProgressUpdate((int)progress);
                        break;
                }
                super.handleMessage(msg);
            }
        };
        stop = false;
    }
    public synchronized static MusicControl getInstance(Context con) {
        if (instance == null) {
            instance = new MusicControl(con);
        }
        return instance;
    }
    public void setMusicFileList(List<Mp3Info> infos){
        mMp3InfoList=infos;
    }

    private void startTimer() {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mLength = getDuration();
                mPosition = getCurrentPosition();
                double d = ((double) mPosition / mLength) * 100;
                Bundle bundle = new Bundle();
                bundle.putDouble("progress", d);
                Message msg = new Message();
                msg.what = WHAT;
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            }
        }, 1000, 500);
    }

    private void stopTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    static List<MusicGressChangeListener> mListeners=new ArrayList<MusicGressChangeListener>();

    public static void setMusicGressChangeListener(MusicGressChangeListener listener) {
        mListeners.add(listener);
    }

    private void callPlayStatChange() {
        for(MusicGressChangeListener listener:mListeners){
            listener.playStatChange(getCurrentMp3Info(),isPlaying(),stop);
        }
    }
    private void calPlayProgressUpdate(int progress){
        for(MusicGressChangeListener listener:mListeners){
            listener.playProgressUpdate(progress);
        }
    }


    public void playMusic() {
        startTimer();
        callPlayStatChange();
        try {
            reset();
            setDataSource(mMp3InfoList.get(mMusicNumber).mPath);
            prepare();
            setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    start();
                    seekTo(0);
                }
            });
            setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    playMusic();
                }
            });
            setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    reset();
                    return true;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void replayMusic() {
        if (!stop) {
            stop = false;
            startTimer();
            callPlayStatChange();
            start();
        } else {
            playMusic();
        }
    }

    public void PauseMusic() {
        stopTimer();
        callPlayStatChange();
        pause();
    }

    public void stopMusic() {
        if (isPlaying()) {
            stop();
        }
        stopTimer();
        callPlayStatChange();
        seekTo(0);
        stop = true;
    }

    public void nextSong() {
        mMusicNumber++;
        if(mMusicNumber>mMp3InfoList.size()){
            mMusicNumber=0;
        }
        playMusic();
        callPlayStatChange();
    }
    public Mp3Info getCurrentMp3Info(){
        return mMp3InfoList.get(mMusicNumber);
    }
}
