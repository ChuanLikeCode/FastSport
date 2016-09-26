package com.sibo.fastsport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibo.fastsport.R;
import com.sibo.fastsport.view.BaseTranslucentActivity;

/**
 * Created by Administrator on 2016/7/25 0025.
 */
public class SettingActivity extends BaseTranslucentActivity {
    //固定的ToolBar
    android.support.v7.widget.Toolbar rootToolBar;
    private ImageView ivClose;
    private ImageView ivBack;
    private Toolbar toolBar;
    private TextView tvText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
        initTitle();
        //back = (ImageView)findViewById(R.id.activity_setting_back);

    }

    private void initView() {


    }

    private void initTitle() {
        rootToolBar = (Toolbar) findViewById(R.id.title);
        toolBar = (Toolbar) rootToolBar.findViewById(R.id.act_title_bar);
        ivClose = (ImageView) rootToolBar.findViewById(R.id.iv_close_titlebar);
        ivBack = (ImageView) rootToolBar.findViewById(R.id.iv_back_titlebar);
        tvText = (TextView) rootToolBar.findViewById(R.id.tv_title_bar);

        ivBack.setVisibility(View.VISIBLE);
        ivClose.setVisibility(View.INVISIBLE);
        tvText.setText("设置");

        setOrChangeTranslucentColor(toolBar, null, getResources().getColor(R.color.title));

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
