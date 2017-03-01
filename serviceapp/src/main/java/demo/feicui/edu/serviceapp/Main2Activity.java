package demo.feicui.edu.serviceapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    HttpClient mClient;
    String mUrl = "http://118.244.212.82:9092/newsClient/news_list?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20";
    String mmUrl = "http://118.244.212.82:9092/newsClient/news_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mClient = new DefaultHttpClient();
                getDataByGet();
            }
        }).start();
    }

    public void getDataByGet() {
        try {
            String result = EntityUtils.toString(mClient.execute(new HttpGet(mUrl)).getEntity());
            Log.i("#############", "#################" + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getDataByPost() {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("ver", "1"));
        params.add(new BasicNameValuePair("subid", "1"));
        params.add(new BasicNameValuePair("dir", "1"));
        params.add(new BasicNameValuePair("nid", "1"));
        params.add(new BasicNameValuePair("stamp", "20140321"));
        params.add(new BasicNameValuePair("cnt", "20"));
//新建HttpPost对象
        HttpPost httpPost = new HttpPost(mmUrl);
//通过List传值给HttpPost对象
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
//新建HttpClient对象
            mClient = new DefaultHttpClient();
//发送请求Post
            HttpResponse httpResponse = mClient.execute(httpPost);
//得到返回值并转化为String
            String result = EntityUtils.toString(httpResponse.getEntity());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
