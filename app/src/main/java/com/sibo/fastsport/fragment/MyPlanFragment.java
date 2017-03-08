package com.sibo.fastsport.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
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
    private static WhorlView whorlView;
    private static TextView tips;
    private MyBombUtils bombUtils;
    private MakePlanUtils makePlanUtils;
    private static RelativeLayout plan_rl;


    private boolean click = false;
    //每一个Day的布局ID
    private int[] daysId = {R.id.ll1, R.id.ll2, R.id.ll3, R.id.ll4, R.id.ll5, R.id.ll6, R.id.ll7,};
    private LinearLayout[] days = new LinearLayout[7];
    //ViewPager的适配器
    private MyDayFragmentAdapter adapter;
    private static ViewPager viewPager;
    //顶部标题栏
    //private Toolbar head;
    //横向ScrollView来显示第一到第七天
    private static HorizontalScrollView hs;
    //第一到第七天的选择图片
    private ImageView[] iv_day = new ImageView[7];
    private int[] iv_dayIds = {R.id.iv1, R.id.iv2, R.id.iv3, R.id.iv4, R.id.iv5, R.id.iv6, R.id.iv7};
    //第一到第七天的Fragment list
    private static List<BaseDay> list_day = new ArrayList<>();
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
    private static Handler completeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 888){
                hs.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.VISIBLE);
                whorlView.stop();
                whorlView.setVisibility(View.GONE);
                tips.setVisibility(View.GONE);
            }
        }
    };
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
                    whorlView.stop();
                    whorlView.setVisibility(View.GONE);
                    hs.setVisibility(View.GONE);
                    viewPager.setVisibility(View.GONE);
                    tips.setVisibility(View.VISIBLE);
                    //Toast.makeText(get,"获取数据失败，请重新扫描",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private View.OnClickListener dayListener = new View.OnClickListener() {
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
    };

    /**
     * 初始化健身模块 七天的。。。。。
     */
    private static void initializePlan() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                classifyPlan();
                initEveryDayWarmUpPlan();
                initEveryDayStretchPlan();
                initEveryDayMainActionPlan();
                initEveryDayRelaxActionPlan();
                showPlan();
                completeHandler.sendEmptyMessage(888);
            }
        }).start();

    }

    /**
     * 展示每天的计划
     */
    private static void showPlan() {
        for (int i = 0; i < list_day.size(); i++) {
            setListViewHeight(list_day.get(i).warmUpListView);
            setListViewHeight(list_day.get(i).stretchingListView);
            setListViewHeight(list_day.get(i).mainActionListView);
            setListViewHeight(list_day.get(i).relaxActionListView);
            list_day.get(i).warmUpAdapter.notifyDataSetChanged();
            list_day.get(i).stretchingAdapter.notifyDataSetChanged();
            list_day.get(i).mainActionAdapter.notifyDataSetChanged();
            list_day.get(i).relaxActionAdapter.notifyDataSetChanged();
            //list_day.get(i).warmUpListView.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 分类初始化每天的热身动作计划
     */
    private static void initEveryDayRelaxActionPlan() {
        for (int i = 0;i<MyBombUtils.list_relaxAction.size();i++){
            for (int j = 0;j<MyBombUtils.list_relaxActionPlan.size();j++){
                if (MyBombUtils.list_relaxAction.get(i).getRelaxAction().equals(MyBombUtils.list_relaxActionPlan.get(j).getObjectId())){
                    switch (MyBombUtils.list_mainAction.get(i).getDayId()){
                        case 1:list_day.get(0).relaxActionList.add(MyBombUtils.list_relaxActionPlan.get(j));break;
                        case 2:list_day.get(1).relaxActionList.add(MyBombUtils.list_relaxActionPlan.get(j));break;
                        case 3:list_day.get(2).relaxActionList.add(MyBombUtils.list_relaxActionPlan.get(j));break;
                        case 4:list_day.get(3).relaxActionList.add(MyBombUtils.list_relaxActionPlan.get(j));break;
                        case 5:list_day.get(4).relaxActionList.add(MyBombUtils.list_relaxActionPlan.get(j));break;
                        case 6:list_day.get(5).relaxActionList.add(MyBombUtils.list_relaxActionPlan.get(j));break;
                        case 7:list_day.get(6).relaxActionList.add(MyBombUtils.list_relaxActionPlan.get(j));break;
                    }
                }
            }

        }
    }
    /**
     * 分类初始化每天的热身动作计划
     */
    private static void initEveryDayMainActionPlan() {
        for (int i = 0;i<MyBombUtils.list_mainAction.size();i++){
            for (int j = 0;j<MyBombUtils.list_mainActionPlan.size();j++){
                if (MyBombUtils.list_mainAction.get(i).getMainAction().equals(MyBombUtils.list_mainActionPlan.get(j).getObjectId())){
                    switch (MyBombUtils.list_mainAction.get(i).getDayId()){
                        case 1:list_day.get(0).mainActionList.add(MyBombUtils.list_mainActionPlan.get(j));break;
                        case 2:list_day.get(1).mainActionList.add(MyBombUtils.list_mainActionPlan.get(j));break;
                        case 3:list_day.get(2).mainActionList.add(MyBombUtils.list_mainActionPlan.get(j));break;
                        case 4:list_day.get(3).mainActionList.add(MyBombUtils.list_mainActionPlan.get(j));break;
                        case 5:list_day.get(4).mainActionList.add(MyBombUtils.list_mainActionPlan.get(j));break;
                        case 6:list_day.get(5).mainActionList.add(MyBombUtils.list_mainActionPlan.get(j));break;
                        case 7:list_day.get(6).mainActionList.add(MyBombUtils.list_mainActionPlan.get(j));break;
                    }
                }
            }

        }
    }
    /**
     * 分类初始化每天的拉伸动作计划
     */
    private static void initEveryDayStretchPlan() {
        for (int i = 0;i<MyBombUtils.list_stretching.size();i++){
            for (int j = 0;j<MyBombUtils.list_stretchPlan.size();j++){
                if (MyBombUtils.list_stretching.get(i).getStretchingId().equals(MyBombUtils.list_stretchPlan.get(j).getObjectId())){
                    switch (MyBombUtils.list_warmUp.get(i).getDayId()){
                        case 1:list_day.get(0).stretchingList.add(MyBombUtils.list_stretchPlan.get(j));break;
                        case 2:list_day.get(1).stretchingList.add(MyBombUtils.list_stretchPlan.get(j));break;
                        case 3:list_day.get(2).stretchingList.add(MyBombUtils.list_stretchPlan.get(j));break;
                        case 4:list_day.get(3).stretchingList.add(MyBombUtils.list_stretchPlan.get(j));break;
                        case 5:list_day.get(4).stretchingList.add(MyBombUtils.list_stretchPlan.get(j));break;
                        case 6:list_day.get(5).stretchingList.add(MyBombUtils.list_stretchPlan.get(j));break;
                        case 7:list_day.get(6).stretchingList.add(MyBombUtils.list_stretchPlan.get(j));break;
                    }
                }
            }

        }
    }

    /**
     * 分类初始化每天的热身动作计划
     */
    private static void initEveryDayWarmUpPlan() {
        for (int i = 0;i<MyBombUtils.list_warmUp.size();i++){
            for (int j = 0;j<MyBombUtils.list_warmPlan.size();j++){
                if (MyBombUtils.list_warmUp.get(i).getWarmId().equals(MyBombUtils.list_warmPlan.get(j).getObjectId())){
                    switch (MyBombUtils.list_warmUp.get(i).getDayId()){
                        case 1:list_day.get(0).warmUpList.add(MyBombUtils.list_warmPlan.get(j));break;
                        case 2:list_day.get(1).warmUpList.add(MyBombUtils.list_warmPlan.get(j));break;
                        case 3:list_day.get(2).warmUpList.add(MyBombUtils.list_warmPlan.get(j));break;
                        case 4:list_day.get(3).warmUpList.add(MyBombUtils.list_warmPlan.get(j));break;
                        case 5:list_day.get(4).warmUpList.add(MyBombUtils.list_warmPlan.get(j));break;
                        case 6:list_day.get(5).warmUpList.add(MyBombUtils.list_warmPlan.get(j));break;
                        case 7:list_day.get(6).warmUpList.add(MyBombUtils.list_warmPlan.get(j));break;
                    }
                }
            }

        }
    }

    /**
     * 分类健身动作
     */
    private static void classifyPlan() {
        MyBombUtils.list_warmPlan.clear();
        MyBombUtils.list_stretchPlan.clear();
        MyBombUtils.list_mainActionPlan.clear();
        MyBombUtils.list_relaxActionPlan.clear();
        for (int i = 0;i<MyBombUtils.list_sportName.size();i++){
            for (int j = 0;j<MyBombUtils.list_warmUp.size();j++){
                if (MyBombUtils.list_sportName.get(i).getObjectId().equals(MyBombUtils.list_warmUp.get(j).getObjectId())){
                    MyBombUtils.list_warmPlan.add(MyBombUtils.list_sportName.get(i));
                }
            }
            for (int  k= 0;k<MyBombUtils.list_stretching.size();k++){
                if (MyBombUtils.list_sportName.get(i).getObjectId().equals(MyBombUtils.list_stretching.get(k).getObjectId())){
                    MyBombUtils.list_stretchPlan.add(MyBombUtils.list_sportName.get(i));
                }
            }
            for (int m = 0;m<MyBombUtils.list_mainAction.size();m++){
                if (MyBombUtils.list_sportName.get(i).getObjectId().equals(MyBombUtils.list_mainAction.get(m).getObjectId())){
                    MyBombUtils.list_mainActionPlan.add(MyBombUtils.list_sportName.get(i));
                }
            }
            for (int n = 0;n<MyBombUtils.list_relaxAction.size();n++){
                if (MyBombUtils.list_sportName.get(i).getObjectId().equals(MyBombUtils.list_relaxAction.get(n).getObjectId())){
                    MyBombUtils.list_relaxActionPlan.add(MyBombUtils.list_sportName.get(i));
                }
            }
        }
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
        scanner.setOnClickListener(this);
        //选择项的监听设置
        for (int i = 0; i < days.length; i++) {
            days[i].setOnClickListener(dayListener);
            iv_day[i].setVisibility(View.GONE);

        }
    }

    /**
     * 获得手机屏幕的宽与ScrollView中子控件的宽
     */
    private void getScreenWH() {

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screen_width = metrics.widthPixels;
        screen_height = metrics.heightPixels;
        day_width = days[0].getWidth();
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

            setListViewHeight(list_day.get(arg0).warmUpListView);
            setListViewHeight(list_day.get(arg0).stretchingListView);
            setListViewHeight(list_day.get(arg0).mainActionListView);
            setListViewHeight(list_day.get(arg0).relaxActionListView);
            list_day.get(arg0).warmUpAdapter.notifyDataSetChanged();
            list_day.get(arg0).stretchingAdapter.notifyDataSetChanged();
            list_day.get(arg0).mainActionAdapter.notifyDataSetChanged();
            list_day.get(arg0).relaxActionAdapter.notifyDataSetChanged();


            list_day.get(arg0).warmUpAdd.setVisibility(View.GONE);
            list_day.get(arg0).stretchingAdd.setVisibility(View.GONE);
            list_day.get(arg0).mainActionAdd.setVisibility(View.GONE);
            list_day.get(arg0).relaxActionAdd.setVisibility(View.GONE);
            list_day.get(arg0).tips.setVisibility(View.GONE);
            list_day.get(arg0).warmUpView.setVisibility(View.VISIBLE);
            list_day.get(arg0).stretchingView.setVisibility(View.VISIBLE);
            list_day.get(arg0).mainActionView.setVisibility(View.VISIBLE);
            list_day.get(arg0).relaxActionView.setVisibility(View.VISIBLE);


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
     * 判断扫描之后返回的数据
     */
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

    /**
     * 扫描二维码后获取服务器数据
     */
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
        bombUtils.getPlanAllName();
        bombUtils.getPlanAllDetail();
    }

    private void findView(View view) {
        plan_rl = (RelativeLayout) view.findViewById(R.id.plan_icd);
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


        MakePlanUtils.context = getActivity();
        //collectPlan = new CollectPlan(this);
        bombUtils = new MyBombUtils(getActivity());
        for (int i = 0; i < 7; i++) {
            BaseDay day = new BaseDay();
            list_day.add(day);//将第一到第七天的Fragment添加到list中
        }
        hs.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);

        resetTextView();
        //初始化Fragment适配器
        adapter = new MyDayFragmentAdapter(getChildFragmentManager(), list_day);
        //ViewPager设置适配器
        viewPager.setAdapter(adapter);
        //viewPager.setOffscreenPageLimit(7);
        viewPager.setCurrentItem(0);//默认显示第一个页面
        //默认选择项为第一个，改变背景颜色
        days[0].setBackgroundColor(getResources().getColor(R.color.light_white));
        //makePlanUtils = new MakePlanUtils(getActivity(), list_day);

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
    public static void setListViewHeight(ListView listView) {
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
