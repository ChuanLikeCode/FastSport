package com.sibo.fastsport.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibo.fastsport.R;
import com.sibo.fastsport.application.Constant;
import com.sibo.fastsport.application.MyApplication;
import com.sibo.fastsport.utils.SharepreferencesUtilSystemSettings;

/**
 * Created by Administrator on 2016/7/25 0025.
 */
public class SettingActivity extends BaseTranslucentActivity implements View.OnClickListener {
    //固定的ToolBar
    android.support.v7.widget.Toolbar rootToolBar;
    private ImageView ivClose;
    private ImageView ivBack;
    private Toolbar toolBar;
    private TextView tvText;
    private TextView complete;

    private Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initTitle();
        initListener();

    }

    private void initListener() {
        exit.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    private void initView() {
        exit = (Button) findViewById(R.id.activity_setting_btn_exit);

    }

    private void initTitle() {
        rootToolBar = (Toolbar) findViewById(R.id.title);
        toolBar = (Toolbar) rootToolBar.findViewById(R.id.act_title_bar);
        ivClose = (ImageView) rootToolBar.findViewById(R.id.iv_close_titlebar);
        ivBack = (ImageView) rootToolBar.findViewById(R.id.iv_back_titlebar);
        tvText = (TextView) rootToolBar.findViewById(R.id.tv_title_bar);
        complete = (TextView) rootToolBar.findViewById(R.id.tv_complete_titlebar);
        complete.setVisibility(View.GONE);
        ivBack.setVisibility(View.VISIBLE);
        ivClose.setVisibility(View.INVISIBLE);
        tvText.setText("设置");
        setOrChangeTranslucentColor(toolBar, null, getResources().getColor(R.color.title));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_setting_btn_exit:
                MyApplication.mUser.setLogin(true);
                SharepreferencesUtilSystemSettings.putValue(this, Constant.ISLOGIN,MyApplication.mUser.getIsLogin());
                startActivity(new Intent(SettingActivity.this,LoginActivity.class));
                finish();
                break;
            case R.id.iv_back_titlebar:
                finish();
                break;
        }
    }
}
