package edu.feicui.app.phone.biz;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.activity.HomeActivity;
import edu.feicui.app.phone.entity.Mp3Info;

/**
 * Created by 似水流年 on 2017/1/17.
 *
 * @author Nicholas.Lv
 */
public class MusicNotifiMgr  implements MusicGressChangeListener {
    private Context mCon;
    private static MusicNotifiMgr instance=null;
    private NotificationManager mNotifiManager;
    private NotificationCompat.Builder mBuilder;
    public RemoteViews mRemotes;
    private MusicControl mMyPlayer;
    private MusicNotifiMgr(Context con){
        this.mCon=con;
        mMyPlayer=MusicControl.getInstance(mCon);
        MusicControl.setMusicGressChangeListener(this);
    }
    public synchronized static MusicNotifiMgr getInstance(Context con){
        if(instance==null){
            instance=new MusicNotifiMgr(con);
        }
        return instance;
    }
    public void createNotification(){
        mNotifiManager = (NotificationManager)mCon.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(mCon);
        mRemotes = new RemoteViews(mCon.getPackageName(), R.layout.item_notification);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher, 0)
                .setAutoCancel(false)
                .setOngoing(true)
                .setTicker("音乐在这里哦！");
        mRemotes.setOnClickPendingIntent(R.id.img_notification_esc, PendingIntent.getBroadcast(mCon,0,new Intent("esc"),0));
        mRemotes.setOnClickPendingIntent(R.id.img_notification_play, PendingIntent.getBroadcast(mCon, 0, new Intent("play/pause"), 0));
        mRemotes.setOnClickPendingIntent(R.id.img_notification_stop, PendingIntent.getBroadcast(mCon, 0, new Intent("stop"), 0));
        mRemotes.setOnClickPendingIntent(R.id.img_notification_next, PendingIntent.getActivity(mCon, 0, new Intent(mCon, HomeActivity.class), 0));
        mBuilder.setContent(mRemotes);
    }
    public void sendNotification() {
        mNotifiManager.notify(1, mBuilder.build());
    }
    public void cancel(){
        if(mNotifiManager!=null){
            mNotifiManager.cancel(1);
        }
    }

    @Override
    public void playStatChange(Mp3Info mp3Info, boolean isPlaying, boolean stoped) {
        if (mRemotes != null && mMyPlayer != null) {
            if (isPlaying) {
                mRemotes.setImageViewResource(R.id.img_notification_play, R.mipmap.play);
            } else {
                mRemotes.setImageViewResource(R.id.img_notification_play, R.mipmap.pause);
            }
            if (!stoped) {
                mRemotes.setTextViewText(R.id.txt_notification_music_name, mp3Info.mTitle);
                mRemotes.setTextViewText(R.id.txt_notification_musicer, mp3Info.mPersion);

            } else {
                mRemotes.setProgressBar(R.id.pgb_notification, 100, 0, false);
                mRemotes.setTextViewText(R.id.txt_notification_music_name, "");
                mRemotes.setTextViewText(R.id.txt_notification_musicer, "");
            }
            sendNotification();
        }
    }

    @Override
    public void playProgressUpdate(int progress) {
        mRemotes.setProgressBar(R.id.pgb_notification,100,progress,false);
        sendNotification();
    }
}
