package com.sibo.fastsport.fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sibo.fastsport.R;
import com.sibo.fastsport.adapter.NewsItemAdapter;
import com.sibo.fastsport.domain.NewsDetails;
import com.sibo.fastsport.utils.XmlParseUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 新闻Fragment
 * Created by Administrator on 2016/11/02.
 */

public class BaseNews extends BaseNewsFragment {

    private static int TAG = 0;//上拉下拉标志位
    private View news;
    private int channel;//频道ID 一共16个频道
    private int index;//标志第几个新闻类别
    private String title;//新闻类别的标题
    private PullToRefreshListView pfl;
    private ListView listView;
    private XmlParseUtils xmlParseUtils;
    private List<NewsDetails> list = new ArrayList<>();
    private NewsItemAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                adapter.notifyDataSetChanged();
                pfl.onRefreshComplete();
            }
        }
    };

    public BaseNews(String title) {
        super(title);
        this.title = title;

    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    @Override
    protected void initData() {

        pfl.setMode(PullToRefreshBase.Mode.BOTH);
        listView = pfl.getRefreshableView();
        adapter = new NewsItemAdapter(getActivity(), list);
        listView.setAdapter(adapter);


    }

    @Override
    protected View initView(LayoutInflater inflater) {
        news = inflater.inflate(R.layout.fragment_news, null);
        findById();
        initListener();
        getNewsData();
        return news;
    }

    private void initListener() {
        pfl.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                TAG = 0;//下拉刷新
                getNewsData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                TAG = 1;//上拉加载
                getNewsData();
            }
        });
    }

    public void getNewsData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取频道--->新闻类别--->新闻Item
                    URL url = new URL(XmlParseUtils.list_channelAndNews
                            .get(channel).getUrl().get(index));
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    InputStream input = connection.getInputStream();
                    xmlParseUtils.pullParseItemXml(input, title);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (TAG == 0) {
                    list.clear();
                    list.addAll(XmlParseUtils.list_details);
                } else {
                    list.addAll(XmlParseUtils.list_details);
                }
                handler.sendEmptyMessage(0);

            }
        }).start();
    }
    private void findById() {
        pfl = (PullToRefreshListView) news.findViewById(R.id.pfl);
        xmlParseUtils = new XmlParseUtils();
    }
}
