package demo.feicui.edu.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by 似水流年 on 2017/1/14.
 *
 * @author Nicholas.Lv
 */
public class AIDLservice extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("###########","AIDL服务已经启动完成");
        return new MyBinder();
    }
    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("###############","onunbind");
        return super.onUnbind(intent);
    }
    class MyBinder extends IMyAidlInterface.Stub {
        public AIDLservice getService(){
            return AIDLservice.this;
        }
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String gaoshiqing() throws RemoteException {
            Log.i("好人方法被引用","###########################");
            return "我是一个大好人，哈哈哈！";
        }
    }
}
