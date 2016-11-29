package com.sibo.fastsport.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.sibo.fastsport.R;

public class ShowWXActivity extends BaseTranslucentActivity {

    private WebView webView;
    private ImageView back;
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
    }

    private void initView() {
        setOrChangeTranslucentColor(findViewById(R.id.showx_rl), getResources().getColor(R.color.title));
        webView = (WebView) findViewById(R.id.showwx_webview);
        back = (ImageView) findViewById(R.id.showx_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
