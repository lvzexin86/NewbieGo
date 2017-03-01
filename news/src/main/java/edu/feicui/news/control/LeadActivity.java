package edu.feicui.news.control;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import edu.feicui.news.R;
import edu.feicui.news.adapter.LeadViewPager;
import edu.feicui.news.common.LogUtil;
import edu.feicui.news.common.SharedPreferencesUtil;
import edu.feicui.news.control.base.BaseActivity;

public class LeadActivity extends BaseActivity {
ViewPager mVpgContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lead);
        if(SharedPreferencesUtil.IsFirstRuning()){
            SharedPreferencesUtil.setUnfirstRun();
            configPagerView();
        }else {
            toNewsMainActivity();
            finish();
        }

        overridePendingTransition(R.anim.intransition,R.anim.overtransition);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public List<View> buildViewList(){
        List<View> views=new ArrayList<View>();
        WindowManager.LayoutParams params=new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
        ImageView img=null;
        img=new ImageView(this);
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        img.setImageDrawable(getDrawable(R.mipmap.small));
        img.setLayoutParams(params);
        views.add(img);
        img=new ImageView(this);
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        img.setImageDrawable(getDrawable(R.mipmap.bd));
        img.setLayoutParams(params);
        views.add(img);
        img=new ImageView(this);
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        img.setImageDrawable(getDrawable(R.mipmap.welcome));
        img.setLayoutParams(params);
        views.add(img);
        img=new ImageView(this);
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        img.setImageDrawable(getDrawable(R.mipmap.wy));
        img.setLayoutParams(params);
        views.add(img);
        img=new ImageView(this);
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        img.setImageDrawable(getDrawable(R.mipmap.logo));
        img.setBackgroundColor(getResources().getColor(R.color.white));
        img.setLayoutParams(params);
        views.add(img);
        return views;
    }
    public void toNewsMainActivity(){
        startActivity(NewsMainActivity.class);
    }
    public void configPagerView(){
        mVpgContent= (ViewPager) findViewById(R.id.vpg_lead_content);
        mVpgContent.setAdapter(new LeadViewPager(buildViewList()));
        mVpgContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LogUtil.i("onPageScrolled---------position"+position);
            }

            @Override
            public void onPageSelected(int position) {
                if(position==4){
                    startActivity(new Intent(LeadActivity.this,NewsMainActivity.class));
                    finish();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                LogUtil.i("onPageScrollStateChanged---------state"+state);
            }
        });
    }
}
