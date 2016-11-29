package com.sibo.fastsport.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.sibo.fastsport.R;

public class ShowWXActivity extends AppCompatActivity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_wx);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        final String url = intent.getStringExtra("url");
        //Log.e("url",url+"");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webSettings.setAllowFileAccess(true);
//        webSettings.setBlockNetworkImage(false);
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                view.loadUrl(url);
//                return true;
//            }
//        });
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.showwx_webview);
    }
}
