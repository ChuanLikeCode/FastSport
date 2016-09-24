package com.sibo.fastsport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
public class RegisterActivity extends AppCompatActivity {
    Button takeIndentify;
    ImageView ivBack, ivClose;
    TextView sendIndentify,receiverSecond,remainSecond,resetIndentify;
    android.support.v7.widget.Toolbar loginTitle;
    TextView tvRegist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

    }

    private void initView() {

        loginTitle = (Toolbar) findViewById(R.id.title);
        ivBack = (ImageView) loginTitle.findViewById(R.id.iv_back_titlebar);
        ivClose = (ImageView) loginTitle.findViewById(R.id.iv_close_titlebar);
        tvRegist = (TextView) loginTitle.findViewById(R.id.tv_title_bar);
        takeIndentify = (Button) findViewById(R.id.takeIndentify);
        sendIndentify = (TextView) findViewById(R.id.tv_sendIdentify);
        receiverSecond = (TextView) findViewById(R.id.tv_receiverSecond);
        remainSecond = (TextView) findViewById(R.id.tv_remainSecond);
        resetIndentify = (TextView) findViewById(R.id.tv_resetIdentify);
        tvRegist.setText("注册");
        ivBack.setVisibility(View.VISIBLE);
        ivClose.setVisibility(View.INVISIBLE);
        sendIndentify.setVisibility(View.GONE);
        receiverSecond.setVisibility(View.GONE);
        remainSecond.setVisibility(View.GONE);
        resetIndentify.setVisibility(View.GONE);
        takeIndentify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIndentify.setVisibility(View.VISIBLE);
                receiverSecond.setVisibility(View.VISIBLE);
                remainSecond.setVisibility(View.VISIBLE);
                resetIndentify.setVisibility(View.VISIBLE);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}
