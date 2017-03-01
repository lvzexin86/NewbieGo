package edu.feicui.app.phone.activity;

import android.annotation.TargetApi;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.base.BaseActivity;

public class LogoActivity extends BaseActivity {
    ImageView mImgLogo;
    AnimationDrawable mAnDraw;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_logo);

        mImgLogo = (ImageView) findViewById(R.id.img_logo_drw);
        mImgLogo.setImageResource(R.drawable.logo_drw);
        mAnDraw = (AnimationDrawable) mImgLogo.getDrawable();
        mAnDraw.setOneShot(true);
        mAnDraw.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(LeadActivity.class);
                finish();
            }
        }).start();
    }
}
