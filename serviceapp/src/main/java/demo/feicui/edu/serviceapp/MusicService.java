package demo.feicui.edu.serviceapp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by 似水流年 on 2017/1/14.
 *
 * @author Nicholas.Lv
 */
public class MusicService extends Service {
    private final int WHAT=1;
    @Nullable

    Messenger messenger=new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Messenger clientMessenger=msg.replyTo;
            switch (msg.what){
                case WHAT:
                   String str=msg.getData().getString("msg");
                    Log.i("#############","收到客户端发来的："+str);
                    Message msg1=new Message();
                    Bundle bundle=new Bundle();
                    bundle.putString("msg","客户端你也好！");
                    msg1.setData(bundle);
                    msg1.what=WHAT;
                    try {
                        clientMessenger.send(msg1);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    });

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("#################", "onBind");
        return messenger.getBinder();
    }

    class MyBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public void onCreate() {
        Log.i("#################", "oncreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("#################", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i("#################", "onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("#################", "onUnBind");
        return super.onUnbind(intent);
    }
}
