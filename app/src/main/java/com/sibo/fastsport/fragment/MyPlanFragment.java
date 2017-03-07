package com.sibo.fastsport.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sibo.fastsport.R;
import com.sibo.fastsport.adapter.MakePlanAdapter;
import com.sibo.fastsport.adapter.MyDayFragmentAdapter;
import com.sibo.fastsport.application.Constant;
import com.sibo.fastsport.ui.ScannerActivity;
import com.sibo.fastsport.utils.MakePlanUtils;
import com.sibo.fastsport.utils.MyBombUtils;
import com.sibo.fastsport.view.WhorlView;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * 扫描二维码得到健身计划
 * Created by zhouchuan on 2017/2/28.
 */

public class MyPlanFragment extends BaseFragment implements View.OnClickListener {


    private String scanner_str;//扫描二维码获得的id
    private View view;
    private TextView title,right;//标题和最右边的
    private ImageView back,close,scanner;//返回键 关闭键 扫描键
    private WhorlView whorlView;
    private TextView tips;
    private MyBombUtils bombUtils;
    private MakePlanUtils makePlanUtils;


    private boolean click = false;
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
    //第一到第七天的选择图片
    private ImageView[] iv_day = new ImageView[7];
    private int[] iv_dayIds = {R.id.iv1, R.id.iv2, R.id.iv3, R.id.iv4, R.id.iv5, R.id.iv6, R.id.iv7};
    //第一到第七天的Fragment list
    private List<BaseDay> list_day = new ArrayList<>();
    //用来保存选择的ViewPager角标
    private boolean[] isSelected = {false, false, false, false, false, false, false};
    //屏幕的宽
    private int screen_width;
    private int screen_height;
    //显示第一到第七天的控件宽度
    private int day_width;
    //当前选择的控件离屏幕左边的距离
    private int day_left;
    //ViewPager滑动时ScrollView跟随滑动的距离
    private int srcollToDis;
    /**
     * 扫描结果处理
     */
    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    initializePlan();//处理扫描得到的健身计划
                    break;
                case Constant.FAILED:
                    Log.e("scanner", "failed");
                    break;
            }
        }
    };
    /**
     * 初始化健身模块 七天的。。。。。
     */
    private static void initializePlan() {

    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_plan, container, false);
        findView(view);
        scannerCode();
        bind();
        return view;
    }

    private void bind() {
        viewPager.setOnPageChangeListener(listener);
        //选择项的监听设置
        for (int i = 0; i < days.length; i++) {
            days[i].setOnClickListener(this);
            iv_day[i].setVisibility(View.GONE);
        }
    }
    /**
     * 当滑动ViewPager时，需要改变选择栏的背景颜色，改变ScrollView滑动的距离
     */
    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int arg0) {
            //计算ScrollView滑动的距离
            day_left = days[arg0].getLeft();
            //滑动的距离是 = 当前控件距离屏幕左边的宽度+控件宽度/2-屏幕宽度/2
            srcollToDis = day_left + day_width / 2 - screen_width / 2;
            //调用smoothScrollTo()滑动ScrollView控件
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
    public void scannerCode(){
        Bundle bundle = getArguments();
        //Log.e("bundle",bundle+"");
        if (bundle != null){
            scanner_str = bundle.getString("scanner");
            if (!scanner_str.equals("failed")){
                getPlanDetail();
                tips.setVisibility(View.GONE);//隐藏提示框
                whorlView.setVisibility(View.VISIBLE);//显示进度条
                whorlView.start();//使进度条动起来
                Toast.makeText(getActivity(),"扫描成功",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(),"扫描失败",Toast.LENGTH_SHORT).show();
            }
            // Log.e("scanner",scanner_str);
        }
    }

    private void getPlanDetail() {
//        IntentFilter filter = new IntentFilter("scannerFinish");
//        receiver = MyBroadcastReceiver.newInstancce();
//        getActivity().registerReceiver(receiver,filter);
        bombUtils = new MyBombUtils(getActivity());
        bombUtils.getUserSportPlan(scanner_str);
        bombUtils.getDayPlan(scanner_str);
        bombUtils.getWarmUp(scanner_str);
        bombUtils.getStretching(scanner_str);
        bombUtils.getMainAction(scanner_str);
        bombUtils.getRelaxAction(scanner_str);
    }

    private void findView(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        hs = (HorizontalScrollView) view.findViewById(R.id.makePlanActivity_scrollView);
        tips = (TextView) view.findViewById(R.id.makePlanFragment_tips);
        whorlView = (WhorlView) view.findViewById(R.id.loading);
        title = (TextView) view.findViewById(R.id.tv_title_bar);
        scanner = (ImageView) view.findViewById(R.id.iv_scanner_titlebar);
        back = (ImageView) view.findViewById(R.id.iv_back_titlebar);
        right = (TextView) view.findViewById(R.id.tv_complete_titlebar);
        close = (ImageView) view.findViewById(R.id.iv_close_titlebar);
        for (int i = 0; i < daysId.length; i++) {
            days[i] = (LinearLayout) view.findViewById(daysId[i]);
        }
        for (int j = 0; j < iv_day.length; j++) {
            iv_day[j] = (ImageView) view.findViewById(iv_dayIds[j]);
        }
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

    @Override
    protected void initData() {

        title.setText("我的健身计划");
        close.setVisibility(View.GONE);
        right.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
        whorlView.setVisibility(View.GONE);//进度条
        scanner.setVisibility(View.VISIBLE);
        scanner.setOnClickListener(this);

        MakePlanUtils.context = getActivity();
        //collectPlan = new CollectPlan(this);
        bombUtils = new MyBombUtils(getActivity());
        for (int i = 0; i < 7; i++) {
            BaseDay day = new BaseDay();
            list_day.add(day);//将第一到第七天的Fragment添加到list中
        }

        resetTextView();
        //初始化Fragment适配器
        adapter = new MyDayFragmentAdapter(getChildFragmentManager(), list_day);
        //ViewPager设置适配器
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);//默认显示第一个页面
        //默认选择项为第一个，改变背景颜色
        days[0].setBackgroundColor(getResources().getColor(R.color.light_white));
        makePlanUtils = new MakePlanUtils(getActivity(), list_day);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_scanner_titlebar:
                if (EasyPermissions.hasPermissions(getActivity(), Manifest.permission.CAMERA)){
                    Intent intent = new Intent(getActivity(), ScannerActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }


    /**
     * 由于ScrollView中嵌套ListView显示的时候，高度只显示一行数据的高度
     * 所以应该计算ListView的原本高度，使用getCount()*item.getMeasuredHeight()
     * 计算高度，重新设置ListView的高度
     *
     * @param listView
     */
    public void setListViewHeight(ListView listView) {
        // 获取ListView对应的Adapter
        MakePlanAdapter adapter = (MakePlanAdapter) listView.getAdapter();
        if (adapter == null) {
            return;
        }
        int totalHeight = 0;
        int len = adapter.getCount();
        View item = adapter.getView(0, null, listView);
        item.measure(0, 0);
        totalHeight = item.getMeasuredHeight() * len;
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setLayoutParams(params);
    }
}
