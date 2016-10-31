package com.sibo.fastsport.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sibo.fastsport.R;
import com.sibo.fastsport.adapter.MyDayFragmentAdapter;
import com.sibo.fastsport.fragment.BaseDay;

import java.util.ArrayList;
import java.util.List;

public class MakePlanActivity extends FragmentActivity implements View.OnClickListener {


    //每一个Day的布局ID
    private int[] daysId = {R.id.ll1, R.id.ll2, R.id.ll3, R.id.ll4, R.id.ll5, R.id.ll6, R.id.ll7,};
    private LinearLayout[] days = new LinearLayout[7];
    //ViewPager的适配器
    private MyDayFragmentAdapter adapter;
    private ViewPager viewPager;
    //顶部标题栏
    private Toolbar head;
    //横向ScrollView来显示第一到第七天
    private HorizontalScrollView hs;
    //页面标题与制定计划选择动作按钮
    private TextView title, make;
    //返回与关闭按钮
    private ImageView back, close;
    //第一到第七天的选择图片
    private ImageView[] iv_day = new ImageView[7];
    private int[] iv_dayIds = {R.id.iv1, R.id.iv2, R.id.iv3, R.id.iv4, R.id.iv5, R.id.iv6, R.id.iv7};
    //第一到第七天的Fragment list
    private List<BaseDay> list_day = new ArrayList<>();
    //用来保存选择的ViewPager角标
    private boolean[] isSelected = {false, false, false, false, false, false, false};
    //屏幕的宽
    private int screen_width;
    //显示第一到第七天的控件宽度
    private int day_width;
    //当前选择的控件离屏幕左边的距离
    private int day_left;
    //ViewPager滑动时ScrollView跟随滑动的距离
    private int srcollToDis;
    /**
     * 制定计划的按钮监听事件
     */
    private View.OnClickListener okListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MakePlanActivity.this, ChooseActionActivity.class));
        }
    };
    /**
     * 返回按钮的监听事件
     */
    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    /**
     * 当滑动ViewPager时，需要改变选择栏的背景颜色，改变ScrollView滑动的距离
     */
    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int arg0) {
            //计算ScrollView滑动的距离
            day_left = days[arg0].getLeft();
            srcollToDis = day_left + day_width / 2 - screen_width / 2;
            hs.smoothScrollTo(srcollToDis, 0);
            //设置选择栏的背景颜色，用来区别哪一个选项被选择了
            resetTextView();
            days[arg0].setBackgroundColor(getResources().getColor(R.color.light_white));
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }
    };
    /**
     * 每一天打钩的那个图片的监听事件
     */
    private View.OnClickListener daysListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < iv_day.length; i++) {
                if (v.getId() == iv_dayIds[i]) {
                    resetTextView();
                    //设置ViewPager需要显示的页面
                    viewPager.setCurrentItem(i);
                    //改变选择项的背景颜色
                    days[i].setBackgroundColor(getResources().getColor(R.color.light_white));
                    //改变选择项的图标背景
                    if (!isSelected[i]) {
                        isSelected[i] = true;
                        iv_day[i].setImageResource(R.mipmap.icon_ok);
                    } else {
                        isSelected[i] = false;
                        iv_day[i].setImageResource(R.mipmap.icon_select_default);
                    }

                }

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_plan);
        findById();
        init();
        initListener();
        getScreenWH();
    }

    /**
     * 设置控件的监听事件
     */
    private void initListener() {
        viewPager.setOnPageChangeListener(listener);
        back.setOnClickListener(backListener);
        make.setOnClickListener(okListener);
        //选择项的监听设置
        for (int i = 0; i < days.length; i++) {
            days[i].setOnClickListener(this);
        }
        //选择图标的监听设置，就是打钩的那个图片
        for (int j = 0; j < iv_day.length; j++) {
            iv_day[j].setOnClickListener(daysListener);
        }
    }

    /**
     * 初始化数据
     */
    private void init() {
        for (int i = 0; i < 7; i++) {
            BaseDay day = new BaseDay();
            list_day.add(day);//将第一到第七天的Fragment添加到list中
        }
        resetTextView();
        //初始化Fragment适配器
        adapter = new MyDayFragmentAdapter(getSupportFragmentManager(), list_day);
        //ViewPager设置适配器
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);//默认显示第一个页面
        //默认选择项为第一个，改变背景颜色
        days[0].setBackgroundColor(getResources().getColor(R.color.light_white));
        //改变标题栏的字符
        title.setText(R.string.makePlan);
        close.setVisibility(View.GONE);
        make.setText(R.string.ok);
    }

    /**
     * 初始化布局文件中的控件，使得Activity中可以操作
     */
    private void findById() {
        // TODO Auto-generated method stub
        for (int i = 0; i < daysId.length; i++) {
            days[i] = (LinearLayout) findViewById(daysId[i]);
        }
        for (int j = 0; j < iv_day.length; j++) {
            iv_day[j] = (ImageView) findViewById(iv_dayIds[j]);
        }
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        head = (Toolbar) findViewById(R.id.makePlanActivity_title);
        title = (TextView) head.findViewById(R.id.tv_title_bar);
        back = (ImageView) head.findViewById(R.id.iv_back_titlebar);
        close = (ImageView) head.findViewById(R.id.iv_close_titlebar);
        make = (TextView) head.findViewById(R.id.tv_complete_titlebar);
        hs = (HorizontalScrollView) findViewById(R.id.makePlanActivity_scrollView);
    }

    /**
     * 重置选项卡的背景颜色
     */
    private void resetTextView() {
        // TODO Auto-generated method stub
        for (int i = 0; i < days.length; i++) {
            days[i].setBackgroundColor(Color.WHITE);
        }
    }

    /**
     * 选项卡的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        for (int i = 0; i < days.length; i++) {
            if (v.getId() == daysId[i]) {
                resetTextView();
                viewPager.setCurrentItem(i);
                days[i].setBackgroundColor(getResources().getColor(R.color.light_white));
                break;
            }
        }
    }

    /**
     * 获得手机屏幕的宽与ScrollView中子控件的宽
     */
    private void getScreenWH() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screen_width = metrics.widthPixels;
        day_width = days[0].getWidth();

    }
}
