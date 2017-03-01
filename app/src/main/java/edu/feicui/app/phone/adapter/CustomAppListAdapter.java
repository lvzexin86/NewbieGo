package edu.feicui.app.phone.adapter;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.base.MyBaseAdapter;
import edu.feicui.app.phone.base.MyBaseApplication;

/**
 * Created by Administrator on 2017/2/8.
 */
public class CustomAppListAdapter extends MyBaseAdapter {
    PackageManager mPackageManager;
    List<PackageInfo> mPackageInfos;
    PackageInfo mPackageInfo;
    ApplicationInfo mAppInfo;
    ViewHolder mViewHolder;
    boolean[] mCheckeds;
    int mFlag;

    public CustomAppListAdapter(List<PackageInfo> packageInfos, int flag) {
        super(packageInfos, R.layout.item_appinfo_list);
        mFlag = flag;
        mPackageManager= MyBaseApplication.getContext().getPackageManager();
        mPackageInfos = packageInfos;
        mCheckeds = new boolean[mPackageInfos.size()];
        setAllChecked(false);
    }

    @Override
    protected Object configViewHolder(Object viewHolder, View view, final int postion) {
        mPackageInfo=mPackageInfos.get(postion);
        mAppInfo=mPackageInfo.applicationInfo;
        if (viewHolder == null && view != null) {
            mViewHolder = new ViewHolder();
            mViewHolder.mCkbSelect = (CheckBox) view.findViewById(R.id.ckb_appList_check);
            mViewHolder.mImgAppIcon = (ImageView) view.findViewById(R.id.img_appList_icon);
            mViewHolder.mTxtAppName = (TextView) view.findViewById(R.id.txt_appList_appName);
            mViewHolder.mTxtAppVersion = (TextView) view.findViewById(R.id.txt_appList_version);
        }else {
            mViewHolder = (ViewHolder) viewHolder;
        }
        mViewHolder.mImgAppIcon.setImageDrawable(mPackageManager.getApplicationIcon(mAppInfo));
        mViewHolder.mTxtAppName.setText(mAppInfo.loadLabel(mPackageManager));
        mViewHolder.mTxtAppVersion.setText(mPackageInfo.versionName);
        mViewHolder.mCkbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCheckeds[postion] = b;
            }
        });
        mViewHolder.mCkbSelect.setChecked(mCheckeds[postion]);
        return mViewHolder;
    }

    static class ViewHolder {
        CheckBox mCkbSelect;
        ImageView mImgAppIcon;
        TextView mTxtAppName, mTxtAppVersion;
    }

    public void setAllChecked(boolean b) {
        for (int i = 0; i < mCheckeds.length; i++) {
            mCheckeds[i] = b;
        }
    }

    public boolean[] getmCheckeds() {
        return mCheckeds;
    }

    public void setDataList(List<PackageInfo> dataList) {
        mPackageInfos = dataList;
    }
}
