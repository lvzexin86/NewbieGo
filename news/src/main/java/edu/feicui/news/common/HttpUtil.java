package edu.feicui.news.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/2/16.
 */
public class HttpUtil {
    //    private static HttpClient mClient;
    //    private static HttpEntity getEntity(String url){
//        mClient= new DefaultHttpClient();
//        HttpEntity entity=null;
//        try {
//            entity=mClient.execute(new HttpGet(url)).getEntity();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return entity;
//    }
//    public static String getStringDataByGet(String url){
//        String result="";
//        try {
//            result=EntityUtils.toString(getEntity(url));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//    public static InputStream getInputStreamDataByGet(String url){
//        try {
//            return getEntity(url).getContent();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    public static String getStringData(String http) {
        StringBuffer result = new StringBuffer();
        InputStream in = getDataStream(http);
        byte[] b = new byte[1024];
        int len;
        try {
            while ((len = in.read(b)) != -1) {
                result.append(new String(b, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static InputStream getDataStream(String http) {
        InputStream in = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(http).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.connect();
            if (connection.getResponseCode() == 200) {
                in = connection.getInputStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }
}
