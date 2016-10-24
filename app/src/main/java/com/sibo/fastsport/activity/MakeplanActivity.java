package com.sibo.fastsport.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sibo.fastsport.R;
import com.sibo.fastsport.adapter.MyFragmentAdapter;
import com.sibo.fastsport.fragment.Day1;
import com.sibo.fastsport.fragment.Day2;
import com.sibo.fastsport.fragment.Day3;
import com.sibo.fastsport.fragment.Day4;
import com.sibo.fastsport.fragment.Day5;
import com.sibo.fastsport.fragment.Day6;
import com.sibo.fastsport.fragment.Day7;

import java.util.ArrayList;
import java.util.List;

public class MakePlanActivity extends FragmentActivity implements View.OnClickListener {

    private int[] daysId = {R.id.ll1, R.id.ll2, R.id.ll3, R.id.ll4, R.id.ll5, R.id.ll6, R.id.ll7,};
    private LinearLayout[] days = new LinearLayout[7];
    private List<Fragment> list = new ArrayList<Fragment>();
    private MyFragmentAdapter fragmentAdapter;
    private ViewPager viewPager;
    private Toolbar head;
    private TextView title, zhiding;
    private ImageView back, close;
    private View.OnClickListener okListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_plan);
        findById();
        init();
        initListener();
    }

    private void initListener() {
        viewPager.setOnPageChangeListener(listener);
        back.setOnClickListener(backListener);
        zhiding.setOnClickListener(okListener);
        for (int i = 0; i < days.length; i++) {
            days[i].setOnClickListener(this);
        }
    }

    private void init() {
        // TODO Auto-generated method stub
        Day1 day1 = new Day1();
        Day2 day2 = new Day2();
        Day3 day3 = new Day3();
        Day4 day4 = new Day4();
        Day5 day5 = new Day5();
        Day6 day6 = new Day6();
        Day7 day7 = new Day7();
        list.add(day1);
        list.add(day2);
        list.add(day3);
        list.add(day4);
        list.add(day5);
        list.add(day6);
        list.add(day7);
        resetTextView();
        fragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(0);
        days[0].setBackgroundColor(Color.WHITE);

        title.setText(R.string.makePlan);
        close.setVisibility(View.GONE);
        zhiding.setText(R.string.ok);
    }

    private void findById() {
        // TODO Auto-generated method stub
        for (int i = 0; i < daysId.length; i++) {
            days[i] = (LinearLayout) findViewById(daysId[i]);
        }
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        head = (Toolbar) findViewById(R.id.makePlanActivity_title);
        title = (TextView) head.findViewById(R.id.tv_title_bar);
        back = (ImageView) head.findViewById(R.id.iv_back_titlebar);
        close = (ImageView) head.findViewById(R.id.iv_close_titlebar);
        zhiding = (TextView) head.findViewById(R.id.tv_complete_titlebar);
    }

    private void resetTextView() {
        // TODO Auto-generated method stub
        for (int i = 0; i < days.length; i++) {
            days[i].setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < days.length; i++) {
            if (v.getId() == daysId[i]) {
                resetTextView();
                viewPager.setCurrentItem(i);
                days[i].setBackgroundColor(getResources().getColor(R.color.light_white));
            }
        }
    }
}
