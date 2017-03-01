package edu.feicui.app.phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.biz.FileMgr;

/**
 * Created by Administrator on 2017/2/15.
 */
public class FileListAdapter extends BaseAdapter {
    ArrayList<FileMgr.FileInfo> mList;
    FileItemViewHolder mHolder;
    Context mCon;
    int mFlag;
    boolean[] mCkbSelecteds;
    public FileListAdapter(Context con,int flag){
        this.mCon=con;
        this.mFlag=flag;
        upDataList();
        mCkbSelecteds=new boolean[mList.size()];
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int position=i;
        if(view==null){
            view=LayoutInflater.from(mCon).inflate(R.layout.item_file_list,null);
            mHolder=new FileItemViewHolder();
            mHolder.mFileName= (TextView) view.findViewById(R.id.txt_item_fileList_fileName);
            mHolder.mFileSize= (TextView) view.findViewById(R.id.txt_item_fileList_fileSize);
            mHolder.mCkbDelete= (CheckBox) view.findViewById(R.id.ckb_item_fileList_delete);
            view.setTag(mHolder);
        }else {
            mHolder= (FileItemViewHolder) view.getTag();
        }
        String name=mList.get(i).mFile.getName();
        if(name.length()>15){
            mHolder.mFileName.setText(name.substring(0,12)+"...");
        }else {
            mHolder.mFileName.setText(name);
        }
        mHolder.mFileSize.setText(mList.get(i).mFile.length()/1024+"k");
        mHolder.mCkbDelete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mList.get(position).setSelected(b);
            }
        });
        return view;
    }
    static class FileItemViewHolder{
        CheckBox mCkbDelete;
        TextView mFileName;
        TextView mFileSize;
    }
    public void upDataList(){
        FileMgr fileMgr=FileMgr.getInstance();
        switch (mFlag){
            case 1:
                mList=fileMgr.getAllFileList();
                break;
            case 2:
                mList=fileMgr.getTxtFileList();
                break;
            case 3:
                mList=fileMgr.getVideoFileList();
                break;
            case 4:
                mList=fileMgr.getMp3FileList();
                break;
            case 5:
                mList=fileMgr.getImgFileList();
                break;
            case 6:
                mList=fileMgr.getZipFileList();
                break;
            case 7:
                mList=fileMgr.getApkFileList();
                break;
        }
    }
}
