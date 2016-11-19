package com.sibo.fastsport.ui;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibo.fastsport.R;
import com.sibo.fastsport.adapter.ChannelAndTypeGridViewAdapter;
import com.sibo.fastsport.adapter.MyNewsFragmentAdapter;
import com.sibo.fastsport.domain.NewsChannel;
import com.sibo.fastsport.fragment.BaseNews;
import com.sibo.fastsport.utils.XmlParseUtils;
import com.sibo.fastsport.view.indicator.PageIndicator;
import com.sibo.fastsport.view.slidingmenu.SlidingMenu;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends FragmentActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    public static TextView tv_preSelected;
    private static int preSelected = 0;
    private ViewPager viewPager;
    private PageIndicator mIndicator;
    private List<BaseNews> list = new ArrayList<BaseNews>();
    private SlidingMenu slidingMenu;
    private ImageView add_newsChannel, back;
    private MyNewsFragmentAdapter adapter;
    private GridView channelGridView;
    private ChannelAndTypeGridViewAdapter channelAndTypeGridViewAdapter;
    private List<String> list_channel;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                initData();
            }
        }
    };
    /* 指示器切换监听 */
    private ViewPager.OnPageChangeListener IndicatorOnPageChangedListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    /* 页卡切换监听 */
    private ViewPager.OnPageChangeListener viewPagerOnPageChangedListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initView();
        initListener();
        if (XmlParseUtils.isFirst) {
            getNewsData();
            XmlParseUtils.isFirst = false;
        } else {
            initData();
            preSelected = 0;
        }
    }


    private void getNewsData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                XmlParseUtils xmlParseUtils = new XmlParseUtils();
                try {
                    URL url = new URL("http://rss.sina.com.cn/sina_all_opml.xml");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    InputStream input = connection.getInputStream();
                    Log.e("getNewsData", "begin");
                    xmlParseUtils.pullParseChannelXml(input);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);

            }
        }).start();
    }


    private void initData() {
        List<String> list_title = XmlParseUtils.list_channelAndNews.get(0).getTitle();
        for (int i = 0; i < list_title.size(); i++) {
            BaseNews baseNews = new BaseNews(list_title.get(i));
            baseNews.setChannel(0);
            baseNews.setIndex(i);
            list.add(baseNews);
        }
        adapter = new MyNewsFragmentAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);
        mIndicator.setViewPager(viewPager);
        list_channel = new ArrayList<>();
        for (NewsChannel n :
                XmlParseUtils.list_channelAndNews) {
            list_channel.add(n.getChannel());
        }
        channelAndTypeGridViewAdapter = new ChannelAndTypeGridViewAdapter(this, list_channel);
        channelGridView.setAdapter(channelAndTypeGridViewAdapter);

    }

    private void initSlidingMenu() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        slidingMenu.setMode(SlidingMenu.RIGHT);
        slidingMenu.setBehindOffset(200);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.sliding_right);

        slidingMenu.setOnCloseListener(new SlidingMenu.OnCloseListener() {
            @Override
            public void onClose() {
                add_newsChannel.startAnimation(StartAnimation(225f, 0f));
            }
        });
    }

    private Animation StartAnimation(float x, float y) {
        RotateAnimation animation = new RotateAnimation(x, y,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setFillAfter(true);
        animation.setDuration(300);
        return animation;
    }

    private void initListener() {
        viewPager.setOnPageChangeListener(viewPagerOnPageChangedListener);
        mIndicator.setOnPageChangeListener(IndicatorOnPageChangedListener);
        add_newsChannel.setOnClickListener(this);
        back.setOnClickListener(this);
        channelGridView.setOnItemClickListener(this);
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.pvr_user_pager);
        mIndicator = (PageIndicator) findViewById(R.id.pvr_user_indicator);
        add_newsChannel = (ImageView) findViewById(R.id.news_add);
        back = (ImageView) findViewById(R.id.news_back);
        initSlidingMenu();
        channelGridView = (GridView) findViewById(R.id.sliding_right_channel_GridView);
        XmlParseUtils.isPreSelectFirst = true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.news_back:
                finish();
                break;
            case R.id.news_add:
                if (!slidingMenu.isMenuShowing()) {
                    add_newsChannel.startAnimation(StartAnimation(0f, 225f));
                    slidingMenu.showMenu();
                }

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView channel = (TextView) view.findViewById(R.id.news_label_items_channel);
        Log.e("onItemClick-preSelected", preSelected + "");
        if (preSelected != position) {
            channel.setBackgroundResource(R.drawable.news_channel_selected_bg);
            channel.setTextColor(getResources().getColor(R.color.white));
            tv_preSelected.setBackgroundResource(R.drawable.news_channel_default_bg);
            tv_preSelected.setTextColor(getResources().getColor(R.color.black));
            preSelected = position;
            tv_preSelected = channel;
        }

    }
}
