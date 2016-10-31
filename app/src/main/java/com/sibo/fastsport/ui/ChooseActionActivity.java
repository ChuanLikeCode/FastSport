package com.sibo.fastsport.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.sibo.fastsport.R;
import com.sibo.fastsport.adapter.MyChooseActionAdapter;
import com.sibo.fastsport.domain.SportName;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ChooseActionActivity extends AppCompatActivity {

    ListView listView;
    SportName nameInfo;
    List<SportName> list_sport = new ArrayList<>();
    MyChooseActionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_action);
        initBmob();
        initView();
        initData();
    }

    private void initData() {
        nameInfo = new SportName();

        BmobQuery<SportName> query = new BmobQuery<SportName>();
        query.findObjects(new FindListener<SportName>() {
            @Override
            public void done(List<SportName> list, BmobException e) {
                adapter = new MyChooseActionAdapter(ChooseActionActivity.this, list);
                listView.setAdapter(adapter);
            }
        });
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.choose_action_listView);
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


}
