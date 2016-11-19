package com.sibo.fastsport.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibo.fastsport.R;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
public class LoginActivity extends BaseTranslucentActivity implements View.OnClickListener {

    //固定的ToolBar
    private Toolbar rootToolBar;
    private ImageView ivClose;
    private ImageView ivBack;
    private TextView title;
    private TextView complete;

    private TextView tvToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initTitle();
        initData();
        initListener();

    }

    private void initData() {
        title.setText(R.string.Login);
        //隐藏顶部栏不需要的控件
        ivBack.setVisibility(View.GONE);
        ivClose.setVisibility(View.GONE);
        complete.setVisibility(View.GONE);
    }

    private void initListener() {
        tvToRegister.setOnClickListener(this);
    }

    private void initTitle() {
        rootToolBar = (Toolbar) findViewById(R.id.login_title);
        ivClose = (ImageView) rootToolBar.findViewById(R.id.iv_close_titlebar);
        ivBack = (ImageView) rootToolBar.findViewById(R.id.iv_back_titlebar);
        title = (TextView) rootToolBar.findViewById(R.id.tv_title_bar);
        complete = (TextView) rootToolBar.findViewById(R.id.tv_complete_titlebar);
        setOrChangeTranslucentColor(rootToolBar, null, getResources().getColor(R.color.title));
    }

    private void initView() {
        tvToRegister = (TextView) findViewById(R.id.tv_register);

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
