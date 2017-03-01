package edu.feicui.news.common;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/2/16.
 */
public class ToastUtil {
    private static Toast mToast;
    public static void showToastLong(Context con,String content){
        if(mToast==null){
            mToast=Toast.makeText(con,content,Toast.LENGTH_LONG);
        }else {
            mToast.setText(content);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }
    public static void showToastShort(Context con,String content){
        if(mToast==null){
            mToast=Toast.makeText(con,content,Toast.LENGTH_SHORT);
        }else {
            mToast.setText(content);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
}
