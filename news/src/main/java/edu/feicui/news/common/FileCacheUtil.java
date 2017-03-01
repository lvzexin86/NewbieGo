package edu.feicui.news.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2017/2/20.
 */
public class FileCacheUtil {
    public static Bitmap getBitmapFromCache(String fileName){
        File file=new File(MBaseApplication.getContext().getCacheDir().getAbsolutePath()+"bitmap/"+fileName);
        Log.i("#########################","fileName="+fileName);
        if(file.exists()){
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }
        return null;
    }
    public static void setBitmapToCache(Bitmap map,String fileName){
        LogUtil.i("存的filename="+fileName);
        File file=new File(MBaseApplication.getContext().getCacheDir().getAbsolutePath()+"bitmap/"+fileName);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        try {
            if(!file.exists()){
                map.compress(Bitmap.CompressFormat.PNG,80,new FileOutputStream(file));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
