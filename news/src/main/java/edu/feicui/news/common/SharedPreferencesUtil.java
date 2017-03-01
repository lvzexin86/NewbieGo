package edu.feicui.news.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/2/23.
 */
public class SharedPreferencesUtil {
    static SharedPreferences mSharedPreferences;
    static SharedPreferences.Editor mEditor;

    static {
        mSharedPreferences = MBaseApplication.getContext().getSharedPreferences("NewsAppConfig", Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static void setUnfirstRun() {
        mEditor.putBoolean(CommonUtil.FIRST_RUN_CONFIG_KEY, false);
        mEditor.commit();
    }

    public static boolean IsFirstRuning() {
        return mSharedPreferences.getBoolean(CommonUtil.FIRST_RUN_CONFIG_KEY, true);
    }
}
