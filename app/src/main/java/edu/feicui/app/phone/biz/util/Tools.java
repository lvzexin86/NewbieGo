package edu.feicui.app.phone.biz.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import edu.feicui.app.phone.entity.Mp3Info;

/**
 * Created by Administrator on 2017/2/13.
 */
public class Tools {
    static ActivityManager mActivityManager;
    static ActivityManager.MemoryInfo mMemoryInfo;

    public static int getCpuCount(){
        File file=new File("/sys/devices/system/cpu/");
        File[] Files=file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if(Pattern.matches("cpu[0-9]", file.getName())){
                    return true;
                }
                return false;
            }
        });
        Log.i("######################","Files.length"+Files.length);
        return Files.length;
    }

    public static void setmMemoryInfo(Context con){
        mMemoryInfo=new ActivityManager.MemoryInfo();
        mActivityManager=(ActivityManager)con.getSystemService(Context.ACTIVITY_SERVICE);
        mActivityManager.getMemoryInfo(mMemoryInfo);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static String getTotalMemory(Context con){
        setmMemoryInfo(con);
        return Long.toString(mMemoryInfo.totalMem/1024/1024);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static String getAvailMemory(Context con){
        setmMemoryInfo(con);
        return Long.toString(mMemoryInfo.availMem/1024/1024);
    }

    public static String getPhoneResolution(Activity activity){
        WindowManager window=activity.getWindowManager();
        Display play=window.getDefaultDisplay();
        int width=play.getWidth();
        int height=play.getHeight();
        Log.i("################","getPhoneResolution="+width+"*"+height);
        return width+"*"+height;
    }

    public static String getCameraResolution(){
//        Camera camera= Camera.open();
//        Camera.Parameters parameters=camera.getParameters();
//        List<Camera.Size> sizes=parameters.getSupportedPictureSizes();
//        Camera.Size size= Collections.max(sizes, new Comparator<Camera.Size>() {
//            @Override
//            public int compare(Camera.Size size, Camera.Size t1) {
//                return size.width-t1.width;
//            }
//        });
//        int count=0;
//        for(Camera.Size size1:sizes){
//            count++;
//            Log.i("#################"+count,"size1.width="+size1.width);
//            Log.i("#################"+count,"size1.height="+size1.height);
//        }
//        String result=size.width+"*"+size.height;
//        Log.i("######################","result"+result);
//        return result;
        return "";
    }

    public static boolean isRoot(){
        if((new File("/system/bin/su").exists())&&(new File("/system/xbin/su").exists())){
            return true;
        }else {
            return false;
        }
    }
    public interface Mp3InfoListListener{
        void over(List<Mp3Info> list);
    }
    public static void getExternalMp3FileInfo(Context con,Mp3InfoListListener Listener){
        List<Mp3Info> mp3InfoList=new ArrayList<Mp3Info>();
        Cursor sor=con.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,MediaStore.Audio.AudioColumns.TITLE);
        while (sor.moveToNext()){
            Log.i("#########################","读取中………………");
            Mp3Info info=new Mp3Info();
            info.mTitle=sor.getString(sor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            info.mPersion=sor.getString(sor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            info.mFileSize=sor.getInt(sor.getColumnIndex(MediaStore.Audio.Media.SIZE));
            info.mPath=sor.getString(sor.getColumnIndex(MediaStore.Audio.Media.DATA));
            info.mTime=sor.getLong(sor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            int isMusic=sor.getInt(sor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
            if(isMusic!=0){
                mp3InfoList.add(info);
            }
        }
        Log.i("#########################","777777777777");
        Listener.over(mp3InfoList);
    }
}
