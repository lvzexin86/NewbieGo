package edu.feicui.app.phone.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/2/16.
 */
public abstract class MyBaseAdapter extends BaseAdapter {
    private List<?> mDataList;
    private int mLayoutId;
    private LayoutInflater mInflater;
    private MyBaseAdapter(){
    }
    public MyBaseAdapter(List<?> list, int layoutId){
        this.mDataList=list;
        mLayoutId=layoutId;
        mInflater=LayoutInflater.from(MyBaseApplication.getContext());
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
            view=mInflater.inflate(mLayoutId,null);
            view.setTag(configViewHolder(null,view,i));
        }else {
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
