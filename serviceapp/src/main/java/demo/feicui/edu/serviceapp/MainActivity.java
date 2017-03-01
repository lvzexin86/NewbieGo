package demo.feicui.edu.serviceapp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import demo.feicui.edu.myapplication.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {

    Button mBtnStart,mBtnStop;
    TextView txt;
    MusicService mMusic;
    ServiceConnection mConnection;
    Messenger serviceMessenger;
    boolean started;
    boolean aidl;
    IMyAidlInterface mAidlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnStart= (Button) findViewById(R.id.btn1);
        mBtnStop= (Button) findViewById(R.id.btn2);
        txt= (TextView) findViewById(R.id.btn3);

        final Intent intent=new Intent();
        intent.setClass(MainActivity.this,MusicService.class);

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("##########","点击启动");
                if(!started){
                    startService(intent);
                    started=true;
                }else{
                    stopService(intent);
                }

            }
        });
        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setPackage("demo.feicui.edu.myapplication");
                intent1.setAction("AIDLSer");
                bindService(intent1, new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        mAidlInterface=IMyAidlInterface.Stub.asInterface(service);
                        try {
                            txt.setText(mAidlInterface.gaoshiqing());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                    }
                },BIND_AUTO_CREATE);
            }
        });
    }
}
