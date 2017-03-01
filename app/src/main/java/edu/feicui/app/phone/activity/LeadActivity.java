package edu.feicui.app.phone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.adapter.MyPagerAdapter;
import edu.feicui.app.phone.base.BaseActivity;
import edu.feicui.app.phone.biz.MySharedPreferences;

public class LeadActivity extends BaseActivity {
    ViewPager mViewPager;
    ImageView mImgSwitch1, mImgSwitch2, mImgSwitch3;
    TextView mTxt;
    int mPosition;
    String mKey;
    MySharedPreferences myShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mKey="isFirst";
        super.onCreate(savedInstanceState);
        myShared=MySharedPreferences.getInStance(this);
        if(myShared.getBoolean(mKey)){
            startActivity(new Intent(LeadActivity.this,HomeActivity.class));
            finish();
        }
        setContentView(R.layout.activity_yindao);
        mViewPager = (ViewPager) findViewById(R.id.yindao_vpg);
        mImgSwitch1 = (ImageView) findViewById(R.id.yindao_switch1);
        mImgSwitch2 = (ImageView) findViewById(R.id.yindao_switch2);
        mImgSwitch3 = (ImageView) findViewById(R.id.yindao_switch3);
        mTxt = (TextView) findViewById(R.id.yindao_txt);
        mTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myShared.putBoolean(mKey,true);
                startActivity(new Intent(LeadActivity.this,HomeActivity.class));
                finish();
            }
        });

        mViewPager.setAdapter(new MyPagerAdapter(getViews()));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (mPosition) {
                    case 0:
                        mImgSwitch1.setImageResource(R.mipmap.adware_style_default);
                        break;
                    case 1:
                        mImgSwitch2.setImageResource(R.mipmap.adware_style_default);
                        break;
                    case 2:
                        mImgSwitch3.setImageResource(R.mipmap.adware_style_default);
                        break;
                }
                switch (position) {
                    case 0:
                        mImgSwitch1.setImageResource(R.mipmap.adware_style_selected);
                        break;
                    case 1:
                        mImgSwitch2.setImageResource(R.mipmap.adware_style_selected);
                        break;
                    case 2:
                        mImgSwitch3.setImageResource(R.mipmap.adware_style_selected);
                        break;
                }
                mPosition = position;
                if (position == 2) {
                    mTxt.setText("跳转");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public ArrayList<View> getViews() {
        ArrayList<View> views = new ArrayList<View>();
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        ImageView img = new ImageView(this);
        img.setLayoutParams(params);
        img.setImageResource(R.mipmap.adware_style_applist);
        views.add(img);
        img = new ImageView(this);
        img.setLayoutParams(params);
        img.setImageResource(R.mipmap.adware_style_banner);
        views.add(img);
        img = new ImageView(this);
        img.setLayoutParams(params);
        img.setImageResource(R.mipmap.adware_style_creditswall);
        views.add(img);
        return views;
    }
}




















