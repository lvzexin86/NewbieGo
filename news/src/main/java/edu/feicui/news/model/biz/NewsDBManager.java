package edu.feicui.news.model.biz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.feicui.news.model.db.DBHelper;
import edu.feicui.news.model.entity.News;

/**
 * Created by Administrator on 2017/2/16.
 */
public class NewsDBManager {
    private DBHelper mMysql;
    private SQLiteDatabase mWrite;
    private SQLiteDatabase mRead;
    private String mSql;
    private static NewsDBManager instance=null;
    private NewsDBManager( ){
        mMysql=new DBHelper();
        mRead=mMysql.getReadableDatabase();
        mWrite=mMysql.getWritableDatabase();
    }
    public synchronized static NewsDBManager getInstance(){
        if(instance==null){
            instance=new NewsDBManager();
        }
        return instance;
    }
    public void AddNewsData(List<News> newses){
        mSql = "insert into news(summary,icon,stamp,title,nid,link,type) values(?,?,?,?,?,?,?);";
        for(News news:newses){
            mWrite.execSQL(mSql,new Object[]{news.mSummary,news.mIcon,news.mStamp,news.mTitle,news.mNid,news.mLink,news.mType});
        }
    }
    public List<News> getNewsData(){
        List<News> newses=new ArrayList<News>();
        mSql="select * from news;";
       Cursor sor=mRead.rawQuery(mSql,null);
        while (sor.moveToNext()){
            News news=new News(sor.getString(0)
                    ,sor.getString(1)
                    ,sor.getString(2)
                    ,sor.getString(3)
                    ,sor.getInt(4)
                    ,sor.getString(5)
                    ,sor.getInt(6));
            newses.add(news);
        }
        return newses;
    }
    public void deleteNewsData(){
        mSql="drop table news;";
        mWrite.execSQL(mSql);
    }
}
