package com.sibo.fastsport.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.sibo.fastsport.R;
import com.sibo.fastsport.adapter.MyChooseActionAdapter;
import com.sibo.fastsport.domain.SportName;
import com.sibo.fastsport.utils.BombUtils;
import com.sibo.fastsport.view.WhorlView;

import java.util.ArrayList;
import java.util.List;

public class ChooseActionActivity extends AppCompatActivity {

    WhorlView whorlView;
    ListView listView;
    BombUtils bombUtils;
    List<SportName> list_sport = new ArrayList<>();
    MyChooseActionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_action);
        initView();
        initData();
        whorlView = (WhorlView) findViewById(R.id.loading);
        whorlView.start();
    }

    private void initData() {
        bombUtils = new BombUtils(this);
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {

                return null;
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                bombUtils.initData();
            }
        }).start();

    }


    private void initView() {
        listView = (ListView) findViewById(R.id.choose_action_listView);
    }

}
