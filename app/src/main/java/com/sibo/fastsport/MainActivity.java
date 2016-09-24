package com.sibo.fastsport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends BaseTranslucentActivity implements View.OnClickListener {
    //固定的ToolBar
    android.support.v7.widget.Toolbar rootToolBar;
    private ImageView ivClose;
    private ImageView ivBack;
    private Toolbar toolBar;
    private TextView tvText;


    private ImageView ivSetting;
    private ImageView ivHead;
    private RelativeLayout rlZhuYe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_my_main);

        initView();
//        initTitle();


    }

    private void initTitle() {
        rootToolBar = (Toolbar) findViewById(R.id.title);
        toolBar = (Toolbar) rootToolBar.findViewById(R.id.act_title_bar);
        ivClose = (ImageView) rootToolBar.findViewById(R.id.iv_close_titlebar);
        ivBack = (ImageView) rootToolBar.findViewById(R.id.iv_back_titlebar);
        tvText = (TextView) rootToolBar.findViewById(R.id.tv_title_bar);
        ivClose.setVisibility(View.INVISIBLE);
        tvText.setText("我的主页");
        ivBack.setVisibility(View.VISIBLE);
        setOrChangeTranslucentColor(toolBar, null, getResources().getColor(R.color.title));

    }

    private void initView() {

        ivHead = (ImageView) findViewById(R.id.activity_my_main_iv_touxiang);
        ivSetting = (ImageView) findViewById(R.id.activity_my_main_iv_setting);
        rlZhuYe = (RelativeLayout) findViewById(R.id.rl_zhuye);
        rlZhuYe.setOnClickListener(this);
        ivHead.setOnClickListener(this);
        ivSetting.setOnClickListener(this);
        //让图片全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_my_main_iv_touxiang:
                Intent PersonalInfoIntent = new Intent(MainActivity.this,PersonalInfoActivity.class);
                startActivity(PersonalInfoIntent);
                finish();
                break;
            case R.id.activity_my_main_iv_setting:
                Intent settingIntent = new Intent(MainActivity.this,SettingActivity.class);
                startActivity(settingIntent);
                finish();
                break;
            case R.id.rl_zhuye:
                Intent myHomeIntent = new Intent(MainActivity.this,PersonalHome.class);
                startActivity(myHomeIntent);
                finish();
        }
    }
}
