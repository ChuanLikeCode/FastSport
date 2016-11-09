package com.sibo.fastsport.ui;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.sibo.fastsport.R;
import com.sibo.fastsport.adapter.MyNewsFragmentAdapter;
import com.sibo.fastsport.fragment.BaseNews;
import com.sibo.fastsport.view.indicator.PageIndicator;
import com.sibo.fastsport.view.slidingmenu.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends FragmentActivity implements View.OnClickListener {

    ViewPager viewPager;
    PageIndicator mIndicator;
    List<BaseNews> list = new ArrayList<BaseNews>();
    SlidingMenu slidingMenu;
    ImageView add_newsChannel, back;
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
        initSlidingMenu();


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
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.pvr_user_pager);
        mIndicator = (PageIndicator) findViewById(R.id.pvr_user_indicator);
        add_newsChannel = (ImageView) findViewById(R.id.news_add);
        back = (ImageView) findViewById(R.id.news_back);
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
}
