package edu.feicui.app.phone.biz;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 似水流年 on 2017/1/11.
 *
 * @author Nicholas.Lv
 */
public class MySharedPreferences {

    private static MySharedPreferences inStance = null;
    private static SharedPreferences mShared;
    private static SharedPreferences.Editor mEditor;

    private MySharedPreferences(Context context) {
        mShared = context.getSharedPreferences("android.txt", Context.MODE_PRIVATE);
        mEditor = mShared.edit();
    }

    public static MySharedPreferences getInStance(Context context) {
        if (inStance == null) {
            inStance = new MySharedPreferences(context);
        }
        return inStance;
    }

    public void putBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public boolean getBoolean(String key) {
        return mShared.getBoolean(key, false);
    }
}
