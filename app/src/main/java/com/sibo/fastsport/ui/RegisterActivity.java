package com.sibo.fastsport.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibo.fastsport.R;

/**
 *
 * Created by Administrator on 2016/9/21 0021.
 */
public class RegisterActivity extends BaseTranslucentActivity {
    //固定的ToolBar
    private Toolbar rootToolBar;
    private Button takeIndentify;
    private TextView sendIndentify, receiverSecond, remainSecond, resetIndentify;
    private ImageView ivClose;
    private ImageView ivBack;
    private TextView title;
    private TextView complete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initTitle();
        initView();
        initListener();
        initData();

    }

    private void initData() {
        title.setText(R.string.Register);
        ivClose.setVisibility(View.GONE);
    }

    private void initListener() {

    }

    private void initTitle() {
        rootToolBar = (Toolbar) findViewById(R.id.register_title);
        ivClose = (ImageView) rootToolBar.findViewById(R.id.iv_close_titlebar);
        ivBack = (ImageView) rootToolBar.findViewById(R.id.iv_back_titlebar);
        title = (TextView) rootToolBar.findViewById(R.id.tv_title_bar);
        complete = (TextView) rootToolBar.findViewById(R.id.tv_complete_titlebar);

        ivBack.setVisibility(View.VISIBLE);
        ivClose.setVisibility(View.INVISIBLE);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        setOrChangeTranslucentColor(rootToolBar, null, getResources().getColor(R.color.title));
    }

    private void initView() {

        takeIndentify = (Button) findViewById(R.id.takeIndentify);
        sendIndentify = (TextView) findViewById(R.id.tv_sendIdentify);
        receiverSecond = (TextView) findViewById(R.id.tv_receiverSecond);
        remainSecond = (TextView) findViewById(R.id.tv_remainSecond);
        resetIndentify = (TextView) findViewById(R.id.tv_resetIdentify);

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

    }
}
