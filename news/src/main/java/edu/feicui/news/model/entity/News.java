package edu.feicui.news.model.entity;

/**
 * Created by Administrator on 2017/2/16.
 */
public class News {
    public String mSummary;
    public String mIcon;
    public String mStamp;
    public String mTitle;
    public int mNid;
    public String mLink;
    public int mType;
    public News(String summary,String icon,String stamp,String title,int nid,String link,int type){
        this.mSummary=summary;
        this.mIcon=icon;
        this.mStamp=stamp;
        this.mTitle=title;
        this.mNid=nid;
        this.mLink=link;
        this.mType=type;
    }
}
