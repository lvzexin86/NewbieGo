package edu.feicui.app.phone.biz.util;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 似水流年 on 2017/1/3.
 *
 * @author Nicholas.Lv
 */
public class Writeto {
    static InputStream mIn;
    static FileOutputStream mOut;
    public static void writeTo(Context con){
        try{
            mIn=con.getAssets().open("commonnum.db",Context.MODE_PRIVATE);
            mOut=con.openFileOutput("mysqlite.db",Context.MODE_PRIVATE);
            byte[] b=new byte[1024];
            int len;
            while ((len=mIn.read(b))!=-1){
                mOut.write(b,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                mIn.close();
                mOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
