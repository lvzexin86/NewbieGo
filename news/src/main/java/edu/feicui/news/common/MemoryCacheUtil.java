package edu.feicui.news.common;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by Administrator on 2017/2/20.
 */
public class MemoryCacheUtil {
   static LruCache<String,Bitmap> mMemoryCache;
    public static void initLruCache(){
        mMemoryCache=new LruCache<String,Bitmap>(getMemoryCacheSize()){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight()/1024;
            }
        };
    }
    public static int getMemoryCacheSize(){
        int systemTotalMemory=((ActivityManager)MBaseApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE))
                .getMemoryClass();
        return 1024*1024*systemTotalMemory/8;
    }

    public static void writeToCache(String url,Bitmap value){
        if(mMemoryCache==null){
            initLruCache();
        }
        if(mMemoryCache.get(url)==null){
            mMemoryCache.put(url,value);
        }
    }

    public static Bitmap getBitmapFromCache(String url){
        if(mMemoryCache==null){
            initLruCache();
        }
        return mMemoryCache.get(url);
    }
}
