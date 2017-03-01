package edu.feicui.app.phone.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.adapter.CustomAppListAdapter;
import edu.feicui.app.phone.base.BaseActivity;
import edu.feicui.app.phone.biz.util.ToastUtil;

public class SoftListActivity extends BaseActivity {
    Toolbar mToolbar;
    ListView mLstAppList;
    Button mBtnDeleteSelected;
    CheckBox mCkbSelectedAll;
    CustomAppListAdapter customAppListAdapter;
    BroadcastReceiver mReceiver;
    PackageManager mPackageManager;
    List<PackageInfo> mPackageInfos;
    int mFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_list);
        mPackageManager=getPackageManager();
        mToolbar=setToolbar();
        mFlag=getIntent().getIntExtra("flag",888);
        mPackageInfos=upDataPackageInfos();
        switch (mFlag){
            case 1:
                setTitle(R.string.scrollview_allApplication);
                break;
            case 2:
                setTitle(R.string.scrollview_systemApplication);
                break;
            case 3:
                setTitle(R.string.scrollview_userApplication);
                break;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addDataScheme("package");
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
                    customAppListAdapter.setDataList(upDataPackageInfos());
                    customAppListAdapter.setAllChecked(false);
                    customAppListAdapter.notifyDataSetChanged();
                }
            }
        };
        registerReceiver(mReceiver, intentFilter);

        mLstAppList = (ListView) findViewById(R.id.lst_appList);
        mBtnDeleteSelected = (Button) findViewById(R.id.btn_soft_list_deleteSelected);
        mCkbSelectedAll = (CheckBox) findViewById(R.id.ckb_softList_deleteAll);

        customAppListAdapter = new CustomAppListAdapter(mPackageInfos,mFlag);

        mLstAppList.setAdapter(customAppListAdapter);

        mBtnDeleteSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final boolean[] checkeds = customAppListAdapter.getmCheckeds();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < checkeds.length; i++) {
                            if (checkeds[i]) {
                                PackageInfo packageInfo = mPackageInfos.get(i);
                                Intent intent = new Intent();
                                intent.setAction(intent.ACTION_DELETE);
                                intent.setData(Uri.parse("package:" + packageInfo.packageName));
                                startActivity(intent);
                            }
                        }
                    }
                }).start();
            }
        });
        mCkbSelectedAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                customAppListAdapter.setAllChecked(b);
                customAppListAdapter.notifyDataSetChanged();
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

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }
    public List<PackageInfo> upDataPackageInfos(){
        if(mPackageInfos!=null){
            mPackageInfos.clear();
        }
        List<PackageInfo> list=mPackageManager.getInstalledPackages(0);
        switch (mFlag){
            case 1:
                mPackageInfos=list;
                break;
            case 2:
                mPackageInfos=new ArrayList<PackageInfo>();
                for(PackageInfo info:list){
                    if((info.applicationInfo.flags& ApplicationInfo.FLAG_SYSTEM)!=0){
                        mPackageInfos.add(info);
                    }
                }
                break;
            case 3:
                mPackageInfos=new ArrayList<PackageInfo>();
                for(PackageInfo info:list){
                    if((info.applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)==0){
                        mPackageInfos.add(info);
                    }
                }
                break;
            case 888:
                ToastUtil.showToastLong(this,"配置数据错误：888");
                break;
        }
        return list;
    }
}
