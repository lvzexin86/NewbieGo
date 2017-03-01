package edu.feicui.app.phone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.base.BaseActivity;
import edu.feicui.app.phone.biz.StorageMent;
import edu.feicui.app.phone.view.StorageArc;

public class SoftMgrActivity extends BaseActivity {
    StorageArc mSa;
    RelativeLayout mRelAllApp,mRelSystemApp,mRelUserApp;
    Intent mIntent;
    ProgressBar mPgbNaturalFree,mPgbExternalFree;
    TextView mTxtNaturalFreeSpace,mTxtExternalFreeSpace;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_mgr);
        setToolbar();
        mSa = (StorageArc) findViewById(R.id.sa);
        mSa.drawAnimation();

        mRelAllApp= (RelativeLayout) findViewById(R.id.Rel_allApplication);
        mRelSystemApp= (RelativeLayout) findViewById(R.id.Rel_systemApplication);
        mRelUserApp= (RelativeLayout) findViewById(R.id.Rel_userApplication);

        mPgbNaturalFree= (ProgressBar) findViewById(R.id.pgb_naturalFreeProportion);
        mPgbExternalFree= (ProgressBar) findViewById(R.id.pgb_externalFreeProportion);

        mPgbNaturalFree.setProgress((int)(StorageMent.getInternalFreeProportion()*100));
        mPgbExternalFree.setProgress((int)(StorageMent.getSDcardFreeProportion()*100));

        mTxtNaturalFreeSpace= (TextView) findViewById(R.id.txt_naturalFreeSpace);
        mTxtExternalFreeSpace= (TextView) findViewById(R.id.txt_externalFreeSpace);

        mTxtNaturalFreeSpace.setText("可用 "+(int)(StorageMent.getInternalFreeStorageSpace()/1048576)+"/"+StorageMent.getInternalTotalStorageSpace()/1048576+"MB");
        if(StorageMent.getExternalIsMounted()){
            mTxtExternalFreeSpace.setText("可用 "+(int)(StorageMent.getSDcardFreeStorageSpace()/1048576)+"/"+StorageMent.getSDcardTotalStorageSpace()/1048576+"MB");
        }else {
            mTxtExternalFreeSpace.setText("没有内存卡");
            mPgbExternalFree.setProgress(0);
        }
        mIntent=new Intent();
        mIntent.setClass(this,SoftListActivity.class);
        mRelAllApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent.putExtra("flag",1);
                startActivity(mIntent);
            }
        });
        mRelSystemApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent.putExtra("flag",2);
                startActivity(mIntent);
            }
        });
        mRelUserApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent.putExtra("flag",3);
                startActivity(mIntent);
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
