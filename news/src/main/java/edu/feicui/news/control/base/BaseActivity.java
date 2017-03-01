package edu.feicui.news.control.base;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import edu.feicui.news.R;
import edu.feicui.news.control.NewsMainActivity;

/**
 * Created by Administrator on 2017/2/16.
 */
public class BaseActivity extends AppCompatActivity{
    Toolbar mTob;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void startActivity(Class c){
        startActivity(new Intent(this,c));
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setActionBar(){
        mTob= (Toolbar) findViewById(R.id.tob_item_style);
        mTob.setNavigationIcon(R.mipmap.ic_launcher);
        mTob.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
        setSupportActionBar(mTob);
        mTob.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toHome();
            }
        });
    }
    private void toHome(){
        if(!getClass().equals(NewsMainActivity.class)){
            Intent intent = new Intent();
            intent.setClass(this, NewsMainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//设置不要刷新将要跳到的界面
            startActivity(intent);
        }
    }
}
