package edu.feicui.app.phone.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/2/16.
 */
public class MyBaseApplication extends Application {
    static Context mCon;
    @Override
    public void onCreate() {
        super.onCreate();
        mCon=this;
    }
    public static Context getContext(){
        return mCon;
    }
}
