package edu.feicui.app.phone.base;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.activity.HomeActivity;

/**
 * Created by 似水流年 on 2016/12/27.
 *
 * @author Nicholas.Lv
 */
public class BaseActivity extends AppCompatActivity {
    Toolbar mToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void startActivity(Class<?> c) {
        startActivity(new Intent(this, c));
    }
    protected void startService(Class<?> c){
        startService(new Intent(this,c));
    }
    protected void bindService(Class<?> c,ServiceConnection serviceConnection){
        bindService(new Intent(this,c),serviceConnection,BIND_AUTO_CREATE);
    }

    protected void startActivity(Class<?> c, Bundle bundle) {
        startActivity(new Intent(this,c).putExtras(bundle));
    }

    protected Toolbar setToolbar(){
        mToolbar= (Toolbar) findViewById(R.id.custom_toolbar_lead);
        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        mToolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.colorFrame));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toHome();
            }
        });
        return mToolbar;
    }
    private void toHome(){
        if(!getClass().equals(HomeActivity.class)){
            Intent intent = new Intent();
            intent.setClass(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//设置不要刷新将要跳到的界面
            startActivity(intent);
        }
    }
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public void setTransition(){
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        Transition explode=TransitionInflater.from(this).inflateTransition(R.transition.explode);
//        getWindow().setEnterTransition(explode);
//    }
}
