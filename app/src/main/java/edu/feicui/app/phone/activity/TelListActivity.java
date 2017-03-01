package edu.feicui.app.phone.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.base.BaseActivity;
import edu.feicui.app.phone.biz.DataManagement;

public class TelListActivity extends BaseActivity {

    int mNo=0;
    String mTitle;
    Intent mIntent;
    Bundle mBundle;
    ListView mLstTelList;
    Toolbar mToolbar;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tel_list);

        mToolbar=setToolbar();

        mIntent=getIntent();
        mBundle=mIntent.getExtras();
        mNo=mBundle.getInt("viewNumber");
        mTitle=mBundle.getString("title");

        setTitle(mTitle);

        mLstTelList= (ListView) findViewById(R.id.lst_tel_list);
        DataManagement data=DataManagement.getInstance();
        SimpleAdapter sim=new SimpleAdapter(this,data.getTelList(mNo),R.layout.item_tel_list
                ,new String[]{"0","1"}
                ,new int[]{R.id.item_txt_tel_list_name,R.id.item_txt_tel_list_number});
        mLstTelList.setAdapter(sim);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void onClick(View v){
        final String number=((TextView)v).getText().toString();
        View view=getLayoutInflater().inflate(R.layout.custom_dailog,null);
        Button btnCancel= (Button) view.findViewById(R.id.custom_daiLog_back);
        Button btnTrue= (Button) view.findViewById(R.id.custom_daiLog_true);
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog dialog=builder.create();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+number));
                startActivity(intent);
                dialog.cancel();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=new MenuInflater(this);
        inflater.inflate(R.menu.custom_title_onlyback,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
