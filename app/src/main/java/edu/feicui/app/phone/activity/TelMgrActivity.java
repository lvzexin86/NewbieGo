package edu.feicui.app.phone.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.base.BaseActivity;
import edu.feicui.app.phone.biz.DataManagement;
import edu.feicui.app.phone.biz.util.Writeto;

/**
 * Created by 似水流年 on 2017/1/3.
 *
 * @author Nicholas.Lv
 */
public class TelMgrActivity extends BaseActivity {
    ListView mLstTelMgr;
    ArrayList<String> mgrdata;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tel_mgr);

        setToolbar();

        mLstTelMgr=(ListView)findViewById(R.id.lst_tel_mgr);

        Writeto.writeTo(this);

        DataManagement data=DataManagement.getInstance();
        mgrdata=data.getTelMgrData();
        mLstTelMgr.setAdapter(new ArrayAdapter(this,R.layout.item_tel_mgr,R.id.item_txt_tel_mgr,mgrdata));
        mLstTelMgr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title=mgrdata.get(position);
                Bundle bundle=new Bundle();
                bundle.putString("title",title);
                bundle.putInt("viewNumber",position);
                startActivity(TelListActivity.class,bundle);
            }
        });

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
