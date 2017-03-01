package edu.feicui.news.common;

import android.util.Log;

/**
 * Created by Administrator on 2017/2/16.
 */
public class LogUtil {
    private static final String TAG="#############################";
    public static boolean mPublish;
    public static void i(String content){
        if(!mPublish){
            Log.i(TAG,content);
        }
    }
}
