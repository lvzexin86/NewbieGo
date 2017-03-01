package edu.feicui.news.common;

/**
 * Created by Administrator on 2017/2/16.
 */
public class CommonUtil {
    //服务器url
    public static final String IP= "http://118.244.212.82:9092/newsClient/news_list?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20";
    //应用数据库名+版本+表名
    public static final String SQLITE_NAME="news";
    public static final int SQLITE_VERSION=1;
    public static final String SQLITE_TABLE_NAME="news";
    //是否第一次运行配置Key
    public static final String FIRST_RUN_CONFIG_KEY="firstRunConfig";
}
