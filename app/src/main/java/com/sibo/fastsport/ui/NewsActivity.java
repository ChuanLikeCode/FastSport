package com.sibo.fastsport.ui;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.sibo.fastsport.R;
import com.sibo.fastsport.adapter.MyNewsFragmentAdapter;
import com.sibo.fastsport.fragment.BaseNews;
import com.sibo.fastsport.view.indicator.PageIndicator;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends FragmentActivity {

    ViewPager viewPager;
    PageIndicator mIndicator;
    List<BaseNews> list = new ArrayList<BaseNews>();
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
        initData();
    }

    private void initData() {
        for (int i = 0; i < 8; i++) {
            BaseNews baseNews = new BaseNews(i);
            list.add(baseNews);
        }
        MyNewsFragmentAdapter adapter = new MyNewsFragmentAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);
        mIndicator.setViewPager(viewPager);
    }

    private void initListener() {
        viewPager.setOnPageChangeListener(viewPagerOnPageChangedListener);
        mIndicator.setOnPageChangeListener(IndicatorOnPageChangedListener);
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.pvr_user_pager);
        mIndicator = (PageIndicator) findViewById(R.id.pvr_user_indicator);
    }
}
