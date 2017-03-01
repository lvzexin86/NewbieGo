package edu.feicui.news.adapter;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.feicui.news.R;
import edu.feicui.news.common.BitMapUtil;
import edu.feicui.news.common.FormatUtil;
import edu.feicui.news.common.LogUtil;
import edu.feicui.news.common.MBaseApplication;
import edu.feicui.news.model.entity.News;
import edu.feicui.news.adapter.base.MyBaseAdapter;


/**
 * Created by Administrator on 2017/2/16.
 */
public class NewsListAdapter extends MyBaseAdapter {
    ViewHolder mHolder;
    List<News> mNewsDataList;
    static boolean mLock;

    public NewsListAdapter(List<News> list) {
        super(list, R.layout.item_main_liststyle);
        mNewsDataList = list;
        mLock=false;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected Object configViewHolder(Object viewHolder, View view, int postion) {
        if (viewHolder == null && view != null) {
            mHolder = new ViewHolder();
            mHolder.mImgIcon = (ImageView) view.findViewById(R.id.img_item_ListStyle_icon);
            mHolder.mTxtTitle = (TextView) view.findViewById(R.id.txt_item_listStyle_title);
            mHolder.mTxtNewsContent = (TextView) view.findViewById(R.id.txt_item_listStyle_content);
            mHolder.mTxtDate = (TextView) view.findViewById(R.id.txt_item_listStyle_date);
        } else {
            mHolder = (ViewHolder) viewHolder;
        }
        if (!mLock) {
            LogUtil.i("##############加载中！！！！！！！！！！！！####");
            Bitmap map = new BitMapUtil().getBitmapBy(mNewsDataList.get(postion).mIcon);
            if (map != null) {
                mHolder.mImgIcon.setImageBitmap(map);
            } else {
                mHolder.mImgIcon.setImageDrawable(MBaseApplication.getContext().getDrawable(R.mipmap.ic_launcher));
            }
        }
        mHolder.mTxtTitle.setText(FormatUtil.CleanSpace(mNewsDataList.get(postion).mTitle));
        mHolder.mTxtNewsContent.setText(FormatUtil.CleanSpace(mNewsDataList.get(postion).mSummary));
        mHolder.mTxtDate.setText(mNewsDataList.get(postion).mStamp);
        return mHolder;
    }

    class ViewHolder {
        public ImageView mImgIcon;
        public TextView mTxtTitle, mTxtNewsContent, mTxtDate;
    }

    public static void setLock(boolean lock) {
        mLock = lock;
    }
}
