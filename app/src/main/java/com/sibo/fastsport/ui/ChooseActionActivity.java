package com.sibo.fastsport.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.sibo.fastsport.R;
import com.sibo.fastsport.adapter.MyChooseActionAdapter;
import com.sibo.fastsport.utils.MyBombUtils;
import com.sibo.fastsport.view.WhorlView;

public class ChooseActionActivity extends AppCompatActivity {
    private final static int SPORT_NAME_FINISH = 1;
    private final static int SPORT_DETAIL_FINISH = 2;
    private WhorlView whorlView;
    private ListView listView;
    private MyBombUtils myBombUtils;
    private MyChooseActionAdapter adapter;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == SPORT_DETAIL_FINISH) {
                whorlView.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);

                adapter = new MyChooseActionAdapter(ChooseActionActivity.this, MyBombUtils.list_sportName);
                listView.setAdapter(adapter);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_action);
        initView();
        initData();
    }

    private void initData() {
        whorlView.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //初始化bmob
                myBombUtils = new MyBombUtils(ChooseActionActivity.this);
                //里面是网络请求会有延迟所以应该在请求的地方设置一个handler发送请求完成的消息
                //然后再来更新UI界面
                myBombUtils.initSportNameData();
                myBombUtils.initSportDetailData();
            }
        }).start();

    }


    private void initView() {
        listView = (ListView) findViewById(R.id.choose_action_listView);
        whorlView = (WhorlView) findViewById(R.id.loading);

    }

}
