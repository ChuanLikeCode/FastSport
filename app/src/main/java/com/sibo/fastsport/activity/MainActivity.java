package com.sibo.fastsport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sibo.fastsport.R;
import com.sibo.fastsport.view.BaseTranslucentActivity;

import cn.bmob.v3.Bmob;

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

        initBmob();
        initView();
//        initTitle();
        initData();


    }

    private void initData() {
      /*  final User obj = new User();
        obj.setName("豆豆");
        obj.setSex("女");
        obj.setAge("18");
        obj.setHeight("165");
        obj.setWeight("55");
        obj.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null){
                    Toast.makeText(MainActivity.this,"数据添加成功"+s,Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"数据添加失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        BmobQuery<User> bmobQuery = new BmobQuery<User>();
        bmobQuery.getObject("dda707bfce", new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if(e == null){
                    Toast.makeText(MainActivity.this,"查询成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"查询失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        obj.setName("更新的豆豆");
        obj.update("dda707bfce", new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    Toast.makeText(MainActivity.this,"更新成功"+obj.getUpdatedAt(),Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"更新失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        obj.setObjectId("89b099c073");
        obj.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    Toast.makeText(MainActivity.this,"删除成功"+obj.getUpdatedAt(),Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"删除失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }

    private void initBmob() {
        //第一：默认初始化
        Bmob.initialize(this, "f79d34f38040f7e7512a4228ea4d0c7a");
        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this);
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);
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
                Intent myHomeIntent = new Intent(MainActivity.this, MyHomeActivity.class);
                startActivity(myHomeIntent);
                finish();
        }
    }
}
