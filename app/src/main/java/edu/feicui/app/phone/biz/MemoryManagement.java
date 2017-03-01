package edu.feicui.app.phone.biz;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by 似水流年 on 2017/1/7.
 *
 * @author Nicholas.Lv
 */
public class MemoryManagement {
    double mResult;
    private static MemoryManagement mInstance = null;
    private Context mCon;
    ActivityManager manager;

    private MemoryManagement(Context con) {
        this.mCon = con;
        manager= (ActivityManager) mCon.getSystemService(Context.ACTIVITY_SERVICE);
    }

    public static MemoryManagement getInstance(Context con) {
        if (mInstance == null) {
            mInstance = new MemoryManagement(con);
        }
        return mInstance;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public double getMemoryUsage(){
        ActivityManager.MemoryInfo info=new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(info);
        mResult=Double.valueOf(new DecimalFormat("#.00").format((double)(info.totalMem-info.availMem)/info.totalMem));
        return mResult;
    }
    public void killAllProcesses(){
        int count=0;
        List<ActivityManager.RunningAppProcessInfo> processInfos=manager.getRunningAppProcesses();
        for(ActivityManager.RunningAppProcessInfo processInfo:processInfos){
            if(processInfo.importance>= ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE){
                String PackageName=processInfo.processName;
                Log.i("#############","清理中，count="+(++count));
                manager.killBackgroundProcesses(PackageName);
            }
        }
    }
}
