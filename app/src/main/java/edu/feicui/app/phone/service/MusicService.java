package edu.feicui.app.phone.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import edu.feicui.app.phone.biz.MusicControl;
import edu.feicui.app.phone.biz.MusicNotifiMgr;

/**
 * Created by 似水流年 on 2017/1/15.
 *
 * @author Nicholas.Lv
 */
public class MusicService extends Service {
    MusicNotifiMgr mMusicNotifiMgr;
    BroadcastReceiver mMp3Receiver;
    private MusicControl mMyPlayer;

    @Nullable
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if (mMyPlayer != null) {
            mMyPlayer.stopMusic();
        }
        mMusicNotifiMgr.cancel();
        if (mMp3Receiver != null) {
            unregisterReceiver(mMp3Receiver);
        }
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mMusicNotifiMgr = MusicNotifiMgr.getInstance(this);
        mMyPlayer = MusicControl.getInstance(this);
        if (!mMyPlayer.isPlaying()) {
            mMyPlayer.playMusic();
        }
        registMBroadCast();
        return super.onStartCommand(intent, flags, startId);
    }

    public void registMBroadCast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("esc");
        filter.addAction("play/pause");
        filter.addAction("stop");
        filter.addAction("next");
        mMp3Receiver = new MusicNotifiBroadcastReceiver();
        registerReceiver(mMp3Receiver, filter);
    }

    class MusicNotifiBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("play/pause".equals(action)) {
                if (mMyPlayer.isPlaying()) {
                    mMyPlayer.PauseMusic();
                } else {
                    mMyPlayer.replayMusic();
                }
            } else if ("stop".equals(action)) {
                mMyPlayer.stopMusic();
            } else if ("next".equals(action)) {
                mMyPlayer.nextSong();
            } else if ("esc".equals(action)) {
                onDestroy();
            }
        }
    }

//    @Override
//    public void playStatChange(Mp3Info mp3Info, boolean isPlaying, boolean stoped) {
//        Log.i("##################","playStatChange");
//        if (mMusicNotifiMgr.mRemotes != null && mMyPlayer != null) {
//            Log.i("##################","playStatChange0000000000");
//            if (isPlaying) {
//                mMusicNotifiMgr.mRemotes.setImageViewResource(R.id.img_notification_play, R.mipmap.pause);
//            } else {
//                mMusicNotifiMgr.mRemotes.setImageViewResource(R.id.img_notification_play, R.mipmap.play);
//            }
//            if (!stoped) {
//                mMusicNotifiMgr.mRemotes.setTextViewText(R.id.txt_notification_music_name, mp3Info.mTitle);
//                mMusicNotifiMgr.mRemotes.setTextViewText(R.id.txt_notification_musicer, mp3Info.mPersion);
//
//            } else {
//                mMusicNotifiMgr.mRemotes.setProgressBar(R.id.pgb_notification, 100, 0, false);
//                mMusicNotifiMgr.mRemotes.setTextViewText(R.id.txt_notification_music_name, "");
//                mMusicNotifiMgr.mRemotes.setTextViewText(R.id.txt_notification_musicer, "");
//            }
//            mMusicNotifiMgr.sendNotification();
//        }
//    }
}
