package edu.feicui.news.control;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import edu.feicui.news.R;
import edu.feicui.news.control.base.BaseActivity;

public class ShowActivity extends BaseActivity {
    String mUrl;
    WebView mWbvShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        setActionBar();

        mWbvShow= (WebView) findViewById(R.id.wbv_show_newsContent);
        mUrl = getIntent().getStringExtra("url");
        mWbvShow.getSettings().setJavaScriptEnabled(true);
        mWbvShow.getSettings().setSupportZoom(true);
        mWbvShow.getSettings().setDisplayZoomControls(true);
        mWbvShow.setWebViewClient(new WebViewClient(){
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });
        if(mUrl!=""){
            mWbvShow.loadUrl(mUrl);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(mWbvShow.canGoBack()){
                mWbvShow.goBack();
            }else {
                finish();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.only_back,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
