package edu.feicui.news.model.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.feicui.news.common.CommonUtil;
import edu.feicui.news.common.MBaseApplication;

/**
 * Created by Administrator on 2017/2/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper() {
        super(MBaseApplication.getContext(), CommonUtil.SQLITE_NAME,null,CommonUtil.SQLITE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists "+CommonUtil.SQLITE_TABLE_NAME+"(summary text,icon text,stamp text,title text,nid int,link text,type int,primary key(nid));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void close(){
        super.close();
    }
}
