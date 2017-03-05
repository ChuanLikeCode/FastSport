package com.sibo.fastsport.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sibo.fastsport.R;
import com.sibo.fastsport.adapter.MakePlanAdapter;
import com.sibo.fastsport.application.Constant;
import com.sibo.fastsport.ui.ScannerActivity;
import com.sibo.fastsport.utils.MakePlanUtils;
import com.sibo.fastsport.utils.MyBombUtils;
import com.sibo.fastsport.view.WhorlView;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * 扫描二维码得到健身计划
 * Created by zhouchuan on 2017/2/28.
 */

public class MyPlanFragment extends BaseFragment implements View.OnClickListener {

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
    private String scanner_str;//扫描二维码获得的id
    private View view;
    private TextView title,right;//标题和最右边的
    private ImageView back,close,scanner;//返回键 关闭键 扫描键
    //    private RelativeLayout[] rl_type = new RelativeLayout[4];//健身计划的四个类型框框
//    private int[] rl_ids = {R.id.base_day_rl_warmUp,R.id.base_day_rl_stretching,R.id.base_day_rl_mainAction,R.id.base_day_rl_relaxAction};
//    private TextView tip;//健身计划的复用多出来的提示框
//    private ListView[] listViews = new ListView[4];//健身计划类型的listview
//    private boolean[] show = {false,false,false,false};//点击操作 隐藏和显示 标识
//    private ImageView[] down = new ImageView[4];//健身类型最左边的那个按钮
//    private int[] down_ids = {R.id.makePlan_iv_warmUpAdd,R.id.makePlan_iv_stretchingAdd,R.id.makePlan_iv_mainActionAdd,R.id.makePlan_iv_relaxActionAdd};
//    private int[] listViewIds = {R.id.makePlan_listView_warmUp,R.id.makePlan_listView_stretching
//                                ,R.id.makePlan_listView_mainAction,R.id.makePlan_listView_relaxAction};
//    private static ListView[] listViewStatic;
//    public static List<SportName> warmUpList = new ArrayList<>();
//    public static List<SportName> stretchingList = new ArrayList<>();
//    public static List<SportName> mainActionList = new ArrayList<>();
//    public static List<SportName> relaxActionList = new ArrayList<>();
//    //创建适配器
//    public static MakePlanAdapter warmUpAdapter;
//    public static MakePlanAdapter stretchingAdapter;
//    public static MakePlanAdapter mainActionAdapter;
//    public static MakePlanAdapter relaxActionAdapter;
    private RelativeLayout plan_rl;
    private WhorlView whorlView;
    private TextView tips;
    //    private MyBroadcastReceiver receiver;
    private MyBombUtils bombUtils;
    private MakePlanUtils makePlanUtils;

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
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //getActivity().unregisterReceiver(receiver);
    }

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
        tips = (TextView) view.findViewById(R.id.makePlanFragment_tips);
        //plan_rl = (RelativeLayout) view.findViewById(R.id.makePlanFragment_rl_plan);
        whorlView = (WhorlView) view.findViewById(R.id.loading);
        title = (TextView) view.findViewById(R.id.tv_title_bar);
        scanner = (ImageView) view.findViewById(R.id.iv_scanner_titlebar);
        back = (ImageView) view.findViewById(R.id.iv_back_titlebar);
        right = (TextView) view.findViewById(R.id.tv_complete_titlebar);
        close = (ImageView) view.findViewById(R.id.iv_close_titlebar);
//        for (int i = 0;i<listViews.length;i++){
//            listViews[i] = (ListView) view.findViewById(listViewIds[i]);
//            rl_type[i] = (RelativeLayout) view.findViewById(rl_ids[i]);
//            down[i] = (ImageView) view.findViewById(down_ids[i]);
//        }
//        tip = (TextView) view.findViewById(R.id.makePlan_tip);
//        listViewStatic = listViews;
    }

    @Override
    protected void initData() {

        title.setText("我的健身计划");
        close.setVisibility(View.GONE);
        right.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
        //tip.setVisibility(View.GONE);
        //plan_rl.setVisibility(View.GONE);//计划模块
        whorlView.setVisibility(View.GONE);//进度条
        scanner.setVisibility(View.VISIBLE);
//        for (int i = 0;i<listViews.length;i++){
//            rl_type[i].setOnClickListener(this);
//            down[i].setOnClickListener(this);
//            down[i].setImageResource(R.drawable.xl_icon_07);
//        }
        scanner.setOnClickListener(this);
//        warmUpAdapter = new MakePlanAdapter(getActivity(), warmUpList);
//        stretchingAdapter = new MakePlanAdapter(getActivity(), stretchingList);
//        mainActionAdapter = new MakePlanAdapter(getActivity(), mainActionList);
//        relaxActionAdapter = new MakePlanAdapter(getActivity(), relaxActionList);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //getActivity().unregisterReceiver(receiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            //点击显示隐藏健身计划
//            case R.id.makePlan_iv_warmUpAdd:
//            case R.id.base_day_rl_warmUp:
//                showListView(0);
//                break;
//            case R.id.makePlan_iv_stretchingAdd:
//            case R.id.base_day_rl_stretching:
//                showListView(1);
//                break;
//            case R.id.makePlan_iv_mainActionAdd:
//            case R.id.base_day_rl_mainAction:
//                showListView(2);
//                break;
//            case R.id.makePlan_iv_relaxActionAdd:
//            case R.id.base_day_rl_relaxAction:
//                showListView(3);
//                break;
            case R.id.iv_scanner_titlebar:
                if (EasyPermissions.hasPermissions(getActivity(), Manifest.permission.CAMERA)){
                    Intent intent = new Intent(getActivity(), ScannerActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

//    public void showListView(int i){
//        if (show[i]){
//            listViews[i].setVisibility(View.VISIBLE);
//            down[i].setImageResource(R.drawable.xl_icon_07);
//            show[i] = false;
//        }else {
//            listViews[i].setVisibility(View.GONE);
//            down[i].setImageResource(R.drawable.hx_icon_10);
//            show[i] = true;
//        }
//    }

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
