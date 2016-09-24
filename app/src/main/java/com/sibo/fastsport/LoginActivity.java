package com.sibo.fastsport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
public class LoginActivity extends BaseTranslucentActivity implements View.OnClickListener {

    //固定的ToolBar
    android.support.v7.widget.Toolbar rootToolBar;
    TextView tvRegister;
    private ImageView ivClose;
    private Toolbar toolBar;
    private TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initTitle();

    }

    private void initTitle() {
        rootToolBar = (Toolbar) findViewById(R.id.title);
        toolBar = (Toolbar) rootToolBar.findViewById(R.id.act_title_bar);
        ivClose = (ImageView) rootToolBar.findViewById(R.id.iv_close_titlebar);
        tvText = (TextView) rootToolBar.findViewById(R.id.tv_title_bar);

        setOrChangeTranslucentColor(toolBar, null, getResources().getColor(R.color.title));


        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initView() {
        tvRegister = (TextView) findViewById(R.id.tv_register);
        tvRegister.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;

        }
    }
}
