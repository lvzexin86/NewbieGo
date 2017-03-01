package edu.feicui.news.adapter.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import edu.feicui.news.common.LogUtil;
import edu.feicui.news.common.MBaseApplication;

/**
 * Created by Administrator on 2017/2/16.
 */
public abstract class MyBaseAdapter extends BaseAdapter {
    private Context mCon;
    private List<?> mDataList;
    private int mLayoutId;
    private MyBaseAdapter(){
    }
    public MyBaseAdapter(List<?> list,int layoutId){
        this.mCon= MBaseApplication.getContext();
        this.mDataList=list;
        mLayoutId=layoutId;
    }
    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view=LayoutInflater.from(mCon).inflate(mLayoutId,null);
            view.setTag(configViewHolder(null,view,i));
        }else {
            LogUtil.i("#############################getView");
            configViewHolder(view.getTag(),null,i);
        }
        return view;
    }
    public void remove(int position){
        if(mDataList!=null){
            mDataList.remove(position);
            notifyDataSetChanged();
        }
    }
    protected abstract Object configViewHolder(Object viewHolder,View view,int postion);
}
