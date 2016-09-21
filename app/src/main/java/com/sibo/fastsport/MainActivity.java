package com.sibo.fastsport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView setting;
    private ImageView head;
    private RelativeLayout zhuye;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_my_main);
        head = (ImageView)findViewById(R.id.activity_my_main_iv_touxiang);
        setting = (ImageView)findViewById(R.id.activity_my_main_iv_setting);
        zhuye = (RelativeLayout)findViewById(R.id.rl_zhuye);
        zhuye.setOnClickListener(this);
        head.setOnClickListener(this);
        setting.setOnClickListener(this);
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
