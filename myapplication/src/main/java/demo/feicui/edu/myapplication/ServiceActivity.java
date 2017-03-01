package demo.feicui.edu.myapplication;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ServiceActivity extends AppCompatActivity {
    private final int WHAT=1;
    boolean bind;
    Intent intent;
    ServiceConnection mConnection;
    Messenger serviceMessenger;
    Button btnSend,btnStart;
    TextView mTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        btnStart= (Button) findViewById(R.id.btn_start);
        mTxt= (TextView) findViewById(R.id.txt111);
        Toolbar bar=(Toolbar) findViewById(R.id.toolbar);
        bar.setTitle("");
        setSupportActionBar(bar);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("#############","点击事情start");
//                startService(new Intent(ServiceActivity.this, AIDLservice.class));
                bindService(new Intent(ServiceActivity.this, AIDLservice.class), new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        Log.i("#####################","onServiceConnected");
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        Log.i("#####################","onServiceDisconnected");
                    }
                },BIND_AUTO_CREATE);
            }
        });

        btnSend =(Button)findViewById(R.id.btn_send);

        intent = new Intent();
        intent.setPackage("demo.feicui.edu.myapplication");
        intent.setAction("sendTo");

        final Messenger clientMessenger=new Messenger(new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case WHAT:
                        String str=msg.getData().getString("msg");
                        mTxt.setTextSize(20);
                        mTxt.setText("收到服务器返回的信息："+str);
                        break;
                }
                super.handleMessage(msg);
            }
        });


        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                bind=true;
                serviceMessenger = new Messenger(service);
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("msg", "您好!");
                msg.setData(bundle);
                msg.replyTo = clientMessenger;
                msg.what = WHAT;
                try {
                    serviceMessenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
//                MusicService.MyBinder binder= (MusicService.MyBinder) service;
                Log.i("###########", "onserviceConnected");
//                mMusic=binder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                bind=false;
                Log.i("###########", "onserviceDisconnected");
            }
        };
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bind) {
                    unbindService(mConnection);

                }
                bindService(intent, mConnection, BIND_AUTO_CREATE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.custom_title,menu);
        return true;
    }
}
