package edu.feicui.app.phone.activity;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.base.BaseActivity;
import edu.feicui.app.phone.biz.util.Tools;

public class PhoneMgrActivity extends BaseActivity {
    ProgressBar mPgbBatteryPower;
    TextView mTxtBatteryPower
            ,mTxtPhoneName,mTxtSystemVersion
            ,mTxtMemory,mTxtMemoryFree
            ,mTxtCpuName,mTxtCpuCount
            ,mTxtPhoneResolution,mTxtCameraResolution
            ,mTxtBaseBandVersion,mTxtHasNotRoot;
    BroadcastReceiver mReceiver;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_phone_mgr);
        setToolbar();

        mPgbBatteryPower= (ProgressBar) findViewById(R.id.pgb_phone_batteryPower);
        mTxtBatteryPower= (TextView) findViewById(R.id.txt_phone_batteryPower);
        mTxtPhoneName= (TextView) findViewById(R.id.txt_phoneMgr_phoneName);
        mTxtSystemVersion= (TextView) findViewById(R.id.txt_phoneMgr_SystemVersion);
        mTxtMemory= (TextView) findViewById(R.id.txt_phoneMgr_memory);
        mTxtMemoryFree= (TextView) findViewById(R.id.txt_phoneMgr_memoryFree);
        mTxtCpuName= (TextView) findViewById(R.id.txt_phoneMgr_cpuName);
        mTxtCpuCount= (TextView) findViewById(R.id.txt_phoneMgr_cpuCount);
        mTxtPhoneResolution= (TextView) findViewById(R.id.txt_phoneMgr_phoneResolution);
        mTxtCameraResolution= (TextView) findViewById(R.id.txt_phoneMgr_cameraResolution);
        mTxtBaseBandVersion= (TextView) findViewById(R.id.txt_phoneMgr_baseBandVersion);
        mTxtHasNotRoot= (TextView) findViewById(R.id.txt_phoneMgr_hasNotRoot);

        registerBatteryReceiver();

        String phoneName=Build.MODEL;
        if(phoneName.length()>18){
            mTxtPhoneName.append(phoneName.substring(0,14)+"...");
        }else {
            mTxtPhoneName.append(phoneName);
        }

        mTxtSystemVersion.append(Build.VERSION.RELEASE);
        mTxtMemory.append(Tools.getTotalMemory(this)+" MB");
        mTxtMemoryFree.append(Tools.getAvailMemory(this)+" MB");
        mTxtCpuName.append(Build.CPU_ABI);
        mTxtCpuCount.append(""+Tools.getCpuCount());
        mTxtPhoneResolution.append(Tools.getPhoneResolution(this));
        mTxtCameraResolution.append(Tools.getCameraResolution());

        mTxtBaseBandVersion.append(Build.RADIO);
        if(Tools.isRoot()){
            mTxtHasNotRoot.append("Root权限");
        }else{
            mTxtHasNotRoot.append("未Root权限");
        }
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
    public void registerBatteryReceiver(){
        IntentFilter filter=new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        filter.addAction(Intent.ACTION_BATTERY_OKAY);
        mReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())){
                    int level=intent.getExtras().getInt("level");
                    int scale=intent.getExtras().getInt("scale");
                    int result=level*100/scale;
                    mTxtBatteryPower.setText(result+"%");
                    mPgbBatteryPower.setProgress(result*95/100);
                }
                if(Intent.ACTION_BATTERY_LOW.equals(intent.getAction())){

                }else if(Intent.ACTION_BATTERY_OKAY.equals(intent.getAction())){

                }
            }
        };
        registerReceiver(mReceiver,filter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}
