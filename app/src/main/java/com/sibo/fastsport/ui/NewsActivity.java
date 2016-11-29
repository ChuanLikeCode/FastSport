package com.sibo.fastsport.ui;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sibo.fastsport.R;
import com.sibo.fastsport.adapter.WXitemAdapter;
import com.sibo.fastsport.domain.WXItem;
import com.sibo.fastsport.utils.WXArticleUtils;

import java.util.ArrayList;
import java.util.List;


public class NewsActivity extends BaseTranslucentActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private final static int SUCCESS = 0;
    //返回键
    private ImageView back;
    //下拉刷新，上拉加载控件  适配器
    private PullToRefreshListView pfl;

    //PullToRefreshListView得到的ListView
    private ListView listView;
    //用于显示的列表集合
    private List<WXItem> wxItemList = new ArrayList<>();
    //微信文章适配器
    private WXitemAdapter adapter;
    private static int TAG = 0;//标记上拉下拉
    private static int offset = 0;//标记每次获取文章的偏移量
    private static int count = 0;//标记第几次获取文章
    private WXArticleUtils wxArticleUtils;//获取微信文章的工具类
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SUCCESS){
                adapter.notifyDataSetChanged();
                pfl.onRefreshComplete();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initView();
        initListener();
        initData();
        getDataWX();
    }

    private void getDataWX(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<WXItem> list = wxArticleUtils.getArticle(5,offset);
                if (TAG == 0){
                    wxItemList.clear();
                    wxItemList.addAll(list);
                }else {
                    wxItemList.addAll(list);
                }
                handler.sendEmptyMessage(SUCCESS);
            }
        }).start();
    }

    private void initData() {
        wxArticleUtils = new WXArticleUtils();
        adapter = new WXitemAdapter(this,wxItemList);
        listView.setAdapter(adapter);
    }

    private void initListener() {
        listView.setOnItemClickListener(this);
        pfl.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                TAG = 0;
                count = 0;
                getDataWX();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                TAG = 1;
                offset = count*5;
                count++;
                getDataWX();
            }
        });
    }

    private void initView() {
        setOrChangeTranslucentColor(findViewById(R.id.news_rl), getResources().getColor(R.color.dip_red));
        pfl = (PullToRefreshListView) findViewById(R.id.wx_pfl);
        pfl.setMode(PullToRefreshBase.Mode.BOTH);
        listView = pfl.getRefreshableView();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(NewsActivity.this,ShowWXActivity.class);
        intent.putExtra("url",wxItemList.get(position).getUrl());
        startActivity(intent);
    }
}









