package edu.feicui.app.phone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.base.BaseActivity;
import edu.feicui.app.phone.biz.FileMgr;

public class FileMgrActivity extends BaseActivity implements FileMgr.SearchFileListener {
    TextView mTxtHasFind,mTxtAll,mTxtTxt,mTxtVideo,mTxtMp3,mTxtImg,mTxtZip,mTxtApk;
    ProgressBar mPgbHasFind,mPgbAll,mPgbTxt,mPgbVideo,mPgbMp3,mPgbImg,mPgbZip,mPgbApk;
    RelativeLayout mRelAll,mRelTxt,mRelVideo,mRelMp3,mRelImg,mRelZip,mRelApk;
    FileMgr mFileMgr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_mgr);
        setToolbar();

        mTxtHasFind= (TextView) findViewById(R.id.txt_fileMgr_hasFind);
        mPgbHasFind= (ProgressBar) findViewById(R.id.pgb_fileMgr_hasFind);
        mTxtAll= (TextView) findViewById(R.id.txt_fileMgr_all);
        mPgbAll= (ProgressBar) findViewById(R.id.pgb_fileMgr_all);
        mRelAll= (RelativeLayout) findViewById(R.id.rel_fileMgr_all);
        mTxtTxt= (TextView) findViewById(R.id.txt_fileMgr_txt);
        mPgbTxt= (ProgressBar) findViewById(R.id.pgb_fileMgr_txt);
        mRelTxt= (RelativeLayout) findViewById(R.id.rel_fileMgr_txt);
        mTxtVideo= (TextView) findViewById(R.id.txt_fileMgr_video);
        mPgbVideo= (ProgressBar) findViewById(R.id.pgb_fileMgr_video);
        mRelVideo= (RelativeLayout) findViewById(R.id.rel_fileMgr_video);
        mTxtMp3= (TextView) findViewById(R.id.txt_fileMgr_mp3);
        mPgbMp3= (ProgressBar) findViewById(R.id.pgb_fileMgr_mp3);
        mRelMp3= (RelativeLayout) findViewById(R.id.rel_fileMgr_mp3);
        mTxtImg= (TextView) findViewById(R.id.txt_fileMgr_img);
        mPgbImg= (ProgressBar) findViewById(R.id.pgb_fileMgr_img);
        mRelImg= (RelativeLayout) findViewById(R.id.rel_fileMgr_img);
        mTxtZip= (TextView) findViewById(R.id.txt_fileMgr_zip);
        mPgbZip= (ProgressBar) findViewById(R.id.pgb_fileMgr_zip);
        mRelZip= (RelativeLayout) findViewById(R.id.rel_fileMgr_zip);
        mTxtApk= (TextView) findViewById(R.id.txt_fileMgr_apk);
        mPgbApk= (ProgressBar) findViewById(R.id.pgb_fileMgr_apk);
        mRelApk= (RelativeLayout) findViewById(R.id.rel_fileMgr_apk);

        mFileMgr=FileMgr.getInstance();
        mFileMgr.setSearchFileListener(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("#######################","run");
                mFileMgr.searchAll();
            }
        }).start();
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
    public void searching(String fileType) {
    }

    @Override
    public void end(boolean isException) {
        if(isException){
            Toast.makeText(this,"文件读取失败",Toast.LENGTH_LONG).show();
        }else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StartOnclick();
                    setLayoutVisibility();
                    loadingData();
                }
            });
        }
    }
    public void StartOnclick(){
        final Intent intent=new Intent();
        intent.setClass(this,FileListActivity.class);
        mRelAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("fileType",1);
                startActivity(intent);
            }
        });
        mRelTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("fileType",2);
                startActivity(intent);
            }
        });
        mRelVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("fileType",3);
                startActivity(intent);
            }
        });
        mRelMp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("fileType",4);
                startActivity(intent);
            }
        });
        mRelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("fileType",5);
                startActivity(intent);
            }
        });
        mRelZip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("fileType",6);
                startActivity(intent);
            }
        });
        mRelApk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("fileType",7);
                startActivity(intent);
            }
        });
    }
    public void setLayoutVisibility(){
        mPgbHasFind.setVisibility(View.INVISIBLE);
        mPgbAll.setVisibility(View.INVISIBLE);
        mPgbTxt.setVisibility(View.INVISIBLE);
        mPgbVideo.setVisibility(View.INVISIBLE);
        mPgbMp3.setVisibility(View.INVISIBLE);
        mPgbImg.setVisibility(View.INVISIBLE);
        mPgbZip.setVisibility(View.INVISIBLE);
        mPgbApk.setVisibility(View.INVISIBLE);

        mTxtHasFind.setVisibility(View.VISIBLE);
        mTxtAll.setVisibility(View.VISIBLE);
        mTxtTxt.setVisibility(View.VISIBLE);
        mTxtVideo.setVisibility(View.VISIBLE);
        mTxtMp3.setVisibility(View.VISIBLE);
        mTxtImg.setVisibility(View.VISIBLE);
        mTxtZip.setVisibility(View.VISIBLE);
        mTxtApk.setVisibility(View.VISIBLE);
    }
    public void loadingData(){
        mTxtHasFind.setText(mFileMgr.getAllFileListSize()/1048576+"MB");
        mTxtAll.setText(mFileMgr.getAllCanSeeFileListSize()/1048576+"MB");
        mTxtTxt.setText(mFileMgr.getTxtFileListSize()/1048576+"MB");
        mTxtVideo.setText(mFileMgr.getVideoFileListSize()/1048576+"MB");
        mTxtMp3.setText(mFileMgr.getMp3FileListSize()/1048576+"MB");
        mTxtImg.setText(mFileMgr.getImgFileListSize()/1048576+"MB");
        mTxtZip.setText(mFileMgr.getZipFileListSize()/1048576+"MB");
        mTxtApk.setText(mFileMgr.getApkFileListSize()/1048576+"MB");
    }
}
