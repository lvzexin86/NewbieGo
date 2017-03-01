package edu.feicui.news.control;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

import edu.feicui.news.R;
import edu.feicui.news.common.BitMapUtil;
import edu.feicui.news.common.CommonUtil;
import edu.feicui.news.common.HttpUtil;
import edu.feicui.news.common.LogUtil;
import edu.feicui.news.control.base.BaseActivity;
import edu.feicui.news.model.biz.NewsDBManager;
import edu.feicui.news.model.biz.NewsParser;
import edu.feicui.news.model.entity.News;
import edu.feicui.news.adapter.NewsListAdapter;

public class NewsMainActivity extends BaseActivity implements BitMapUtil.ImageLoadListener,NewsParser.ParserOverListener{
    ListView mLsvNewsContent;
    List<News> mNewsDataList;
    NewsListAdapter mNewsListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_main);
        mLsvNewsContent= (ListView) findViewById(R.id.lsv_main_newsList);
        mLsvNewsContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(NewsMainActivity.this,ShowActivity.class);
                intent.putExtra("url",mNewsDataList.get(i).mLink);
                startActivity(intent);
            }
        });
        mLsvNewsContent.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                LogUtil.i("#####################onScrollStateChanged");
                NewsListAdapter.setLock(false);
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                NewsListAdapter.setLock(true);
            }
        });
        mNewsDataList=NewsDBManager.getInstance().getNewsData();
        BitMapUtil.setImageLoadListener(this);
        if(mNewsDataList==null||mNewsDataList.size()==0){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NewsParser.parserData(HttpUtil.getStringData(CommonUtil.IP),NewsMainActivity.this);
                }
            }).start();
        }else {
            over(mNewsDataList);
        }
    }

    @Override
    public void imageLoadOver(Bitmap map, String url) {
        ImageView img=((ImageView)mLsvNewsContent.findViewWithTag(url));
        LogUtil.i("##########################imageLoadOver");
        if(img!=null){
            img.setImageBitmap(map);
            mNewsListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void over(List<News> newses) {
        mNewsDataList=newses;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLsvNewsContent.setAdapter(new NewsListAdapter(mNewsDataList));
            }
        });
    }
}
