package edu.feicui.app.phone.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.base.BaseActivity;

public class HomeActivity extends BaseActivity {
    TextView mTxtRocket, mTxtSoftMgr, mTxtPhoneMgr,
            mTxtTelMgr, mTxtFileMgr, mTxtSdClean;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);

        mToolbar = setToolbar();

        mTxtRocket = (TextView) findViewById(R.id.lead_txt_rocket);
        mTxtSoftMgr = (TextView) findViewById(R.id.lead_txt_softmgr);
        mTxtPhoneMgr = (TextView) findViewById(R.id.lead_txt_phonemgr);
        mTxtTelMgr = (TextView) findViewById(R.id.lead_txt_telmgr);
        mTxtFileMgr = (TextView) findViewById(R.id.lead_txt_filemgr);
        mTxtSdClean = (TextView) findViewById(R.id.lead_txt_sdclean);

        mTxtRocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mTxtSoftMgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SoftMgrActivity.class);
            }
        });
        mTxtPhoneMgr.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                startActivity(PhoneMgrActivity.class);
            }
        });
        mTxtTelMgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TelMgrActivity.class);
            }
        });
        mTxtFileMgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(FileMgrActivity.class);
            }
        });
        mTxtSdClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v, new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.music:
                                startActivity(PlayerActivity.class);
                                break;
                        }
                        return true;
                    }
                });

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.custom_title, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.submenu_music:
                startActivity(PlayerActivity.class);
                break;
            case R.id.menu_set:
                startActivity(ConfigActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        return super.onPrepareOptionsPanel(view, menu);
    }

    public void showPopupMenu(View v, PopupMenu.OnMenuItemClickListener listener) {
        PopupMenu pop = new PopupMenu(this, v);
        getMenuInflater().inflate(R.menu.custom_menu, pop.getMenu());
        pop.setOnMenuItemClickListener(listener);
        pop.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
