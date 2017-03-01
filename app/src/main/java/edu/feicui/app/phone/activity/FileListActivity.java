package edu.feicui.app.phone.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.adapter.FileListAdapter;
import edu.feicui.app.phone.base.BaseActivity;
import edu.feicui.app.phone.biz.FileMgr;

public class FileListActivity extends BaseActivity {
    TextView mTxtFileCount, mTxtFileSpace;
    ListView mLstContent;
    Button mBtnDelete;
    Toolbar mToolbar;
    FileMgr mFileMgr;
    int mFileType;
    FileListAdapter mAdapter;
    ArrayList<FileMgr.FileInfo> mList;
    int mFileCount;
    long mListSize;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);
        setToolbar();
        mFileMgr=FileMgr.getInstance();
        mFileType = getIntent().getIntExtra("fileType", 888);
        Log.i("######################", "fileType=" + mFileType);
        switch (mFileType) {
            case 1:
                setTitle(R.string.fileMgr_all);
                mList=mFileMgr.getAllFileList();
                mListSize=mFileMgr.getAllFileListSize();
                break;
            case 2:
                setTitle(R.string.fileMgr_txt);
                mList=mFileMgr.getTxtFileList();
                mListSize=mFileMgr.getTxtFileListSize();
                break;
            case 3:
                setTitle(R.string.fileMgr_video);
                mList=mFileMgr.getVideoFileList();
                mListSize=mFileMgr.getVideoFileListSize();
                break;
            case 4:
                setTitle(R.string.fileMgr_mp3);
                mList=mFileMgr.getMp3FileList();
                mListSize=mFileMgr.getMp3FileListSize();
                break;
            case 5:
                setTitle(R.string.fileMgr_img);
                mList=mFileMgr.getImgFileList();
                mListSize=mFileMgr.getImgFileListSize();
                break;
            case 6:
                setTitle(R.string.fileMgr_zip);
                mList=mFileMgr.getZipFileList();
                mListSize=mFileMgr.getZipFileListSize();
                break;
            case 7:
                setTitle(R.string.fileMgr_apk);
                mList=mFileMgr.getApkFileList();
                mListSize=mFileMgr.getApkFileListSize();
                break;
        }
        mTxtFileCount = (TextView) findViewById(R.id.txt_fileList_fileCount);
        mTxtFileSpace = (TextView) findViewById(R.id.txt_fileList_fileSpace);
        mLstContent = (ListView) findViewById(R.id.lst_fileList_content);
        mBtnDelete = (Button) findViewById(R.id.btn_fileList_delete);

        mTxtFileCount.setText(mList.size()+"个");
        mTxtFileSpace.setText(mListSize / 1048576 + "MB");
        mAdapter=new FileListAdapter(this,mFileType);
        mLstContent.setAdapter(mAdapter);
        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFileMgr.deleteSeletedFile(mList);
                //数据没有更新完成，需要删除adapter里面list中的数据
                mAdapter.upDataList();
                mAdapter.notifyDataSetChanged();
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
    protected void onPause() {
        mFileMgr.setIsStopSearch();
        super.onPause();
    }
}
