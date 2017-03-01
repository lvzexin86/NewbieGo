package edu.feicui.app.phone.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.base.BaseActivity;

public class ConfigActivity extends BaseActivity {
    RelativeLayout mRelAbout, mRelVersion, mRelShare, mRelOpinion, mRelHelp;
    ToggleButton mTgbOpen,mTgbNotifition,mTgbNews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        setToolbar();

        mTgbOpen= (ToggleButton) findViewById(R.id.tgb_config_open);
        mTgbNotifition= (ToggleButton) findViewById(R.id.tgb_config_notifi);
        mTgbNews= (ToggleButton) findViewById(R.id.tgb_config_news);

        mRelAbout = (RelativeLayout) findViewById(R.id.rel_config_about);
        mRelVersion = (RelativeLayout) findViewById(R.id.rel_config_version);
        mRelShare = (RelativeLayout) findViewById(R.id.rel_config_share);
        mRelOpinion = (RelativeLayout) findViewById(R.id.rel_config_opinion);
        mRelHelp = (RelativeLayout) findViewById(R.id.rel_config_help);

        mRelAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AboutUsActivity.class);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.custom_title_onlyback, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
