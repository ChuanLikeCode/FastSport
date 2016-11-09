package com.sibo.fastsport.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sibo.fastsport.R;
import com.sibo.fastsport.adapter.MyChooseActionAdapter;
import com.sibo.fastsport.domain.SportName;
import com.sibo.fastsport.utils.ChooseDialogUtils;
import com.sibo.fastsport.utils.MyBombUtils;
import com.sibo.fastsport.view.WhorlView;

import java.util.ArrayList;
import java.util.List;

public class ChooseActionActivity extends AppCompatActivity implements View.OnClickListener {
    //完成网络请求标志 sportName = 1 sportDetail = 2
    private final static int SPORT_NAME_FINISH = 1;
    private final static int SPORT_DETAIL_FINISH = 2;
    //进度条
    private WhorlView whorlView;
    //part--不限部位 equipment--不限机械  action_type--动作类型  level---全部难度
    private String[] part_tpye = {"胸部", "背部", "腿部", "肱二头肌", "肱三头肌", "肩部", "前臂", "腹部"};
    private String[] equipment_type = {"哑铃", "健身房器械", "无器械", "杠铃"};
    private String[] action_type = {"热身动作", "拉伸", "具体动作", "放松动作"};
    private String[] level_type = {"零基础", "初学", "进阶", "强化", "挑战"};
    private TextView[] peal = new TextView[4];
    private ImageView[] iv_peal = new ImageView[4];
    private int[] pealIds = {R.id.choose_part, R.id.choose_equipment,
            R.id.choose_type, R.id.choose_level};
    private int[] iv_pealIds = {R.id.choose_part_down, R.id.choose_equipment_down,
            R.id.choose_type_down, R.id.choose_level_down};
    //显示健身动作的listView
    private ListView listView;
    private List<SportName> list = new ArrayList<>();
    //bmob服务器的工具类
    private MyBombUtils myBombUtils;
    //listView的适配器
    private MyChooseActionAdapter adapter;
    private ChooseDialogUtils chooseDialogUtils;


    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == SPORT_DETAIL_FINISH) {
                whorlView.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                list.addAll(MyBombUtils.list_sportName);
                adapter = new MyChooseActionAdapter(ChooseActionActivity.this, list);
                chooseDialogUtils = new ChooseDialogUtils(ChooseActionActivity.this, list, adapter);
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
        initListener();
    }

    private void initListener() {
        for (int i = 0; i < pealIds.length; i++) {
            peal[i].setOnClickListener(this);
            iv_peal[i].setOnClickListener(this);
        }
    }

    private void initData() {
        whorlView.start();
        if (MyBombUtils.isFirst) {
            MyBombUtils.isFirst = false;
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
        } else {
            whorlView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            list.addAll(MyBombUtils.list_sportName);
            adapter = new MyChooseActionAdapter(ChooseActionActivity.this, list);
            chooseDialogUtils = new ChooseDialogUtils(this, list, adapter);
            listView.setAdapter(adapter);
        }


    }


    private void initView() {
        listView = (ListView) findViewById(R.id.choose_action_listView);
        whorlView = (WhorlView) findViewById(R.id.loading);
        for (int i = 0; i < pealIds.length; i++) {
            peal[i] = (TextView) findViewById(pealIds[i]);
            iv_peal[i] = (ImageView) findViewById(iv_pealIds[i]);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_part:
            case R.id.choose_part_down:
                chooseDialogUtils.showDialog(0);
                break;
            case R.id.choose_equipment:
            case R.id.choose_equipment_down:
                chooseDialogUtils.showDialog(1);
                break;
            case R.id.choose_type:
            case R.id.choose_type_down:
                chooseDialogUtils.showDialog(2);
                break;
            case R.id.choose_level:
            case R.id.choose_level_down:
                chooseDialogUtils.showDialog(3);
                break;
        }
    }
}
