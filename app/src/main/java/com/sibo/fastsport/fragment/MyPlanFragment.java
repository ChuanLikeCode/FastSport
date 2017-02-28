package com.sibo.fastsport.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sibo.fastsport.R;
import com.sibo.fastsport.ui.ScannerActivity;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * 扫描二维码得到健身计划
 * Created by zhouchuan on 2017/2/28.
 */

public class MyPlanFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ImageView iv_warm_down,iv_strething_down,iv_main_down,iv_relax_down;
    private TextView title,right;
    private ImageView back,close,scanner;
    private RelativeLayout[] rl_type = new RelativeLayout[4];
    private int[] rl_ids = {R.id.base_day_rl_warmUp,R.id.base_day_rl_stretching,R.id.base_day_rl_mainAction,R.id.base_day_rl_relaxAction};
    private TextView tip;
    private ListView[] listViews = new ListView[4];
    private boolean[] show = {false,false,false,false};
    private int[] listViewIds = {R.id.makePlan_listView_warmUp,R.id.makePlan_listView_stretching
                                ,R.id.makePlan_listView_mainAction,R.id.makePlan_listView_relaxAction};
    @Override
    protected View initView(LayoutInflater inflater) {
        view  = inflater.inflate(R.layout.fragment_plan,null);
        findView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Bundle bundle = getArguments();
        Log.e("scanner",bundle+"");
        if (bundle != null){
            String scanner = bundle.getString("scanner");
            Log.e("scanner",scanner);
        }
    }

    private void findView(View view) {
        iv_main_down = (ImageView) view.findViewById(R.id.makePlan_iv_mainActionAdd);
        iv_relax_down = (ImageView) view.findViewById(R.id.makePlan_iv_relaxActionAdd);
        iv_strething_down = (ImageView) view.findViewById(R.id.makePlan_iv_stretchingAdd);
        iv_warm_down = (ImageView) view.findViewById(R.id.makePlan_iv_warmUpAdd);
        title = (TextView) view.findViewById(R.id.tv_title_bar);
        scanner = (ImageView) view.findViewById(R.id.iv_scanner_titlebar);
        back = (ImageView) view.findViewById(R.id.iv_back_titlebar);
        right = (TextView) view.findViewById(R.id.tv_complete_titlebar);
        close = (ImageView) view.findViewById(R.id.iv_close_titlebar);
        for (int i = 0;i<listViews.length;i++){
            listViews[i] = (ListView) view.findViewById(listViewIds[i]);
            rl_type[i] = (RelativeLayout) view.findViewById(rl_ids[i]);
        }
        tip = (TextView) view.findViewById(R.id.makePlan_tip);
    }

    @Override
    protected void initData() {
        title.setText("我的健身计划");
        close.setVisibility(View.GONE);
        right.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
        tip.setVisibility(View.GONE);
        scanner.setVisibility(View.VISIBLE);
        for (int i = 0;i<listViews.length;i++){
            rl_type[i].setOnClickListener(this);
        }
        scanner.setOnClickListener(this);
        iv_warm_down.setImageResource(R.drawable.xl_icon_07);
        iv_strething_down.setImageResource(R.drawable.xl_icon_07);
        iv_main_down.setImageResource(R.drawable.xl_icon_07);
        iv_relax_down.setImageResource(R.drawable.xl_icon_07);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击显示隐藏健身计划
            case R.id.makePlan_iv_warmUpAdd:
            case R.id.base_day_rl_warmUp:
                iv_warm_down.setImageResource(R.drawable.hx_icon_10);
                showListView(0);
                break;
            case R.id.makePlan_iv_stretchingAdd:
            case R.id.base_day_rl_stretching:
                iv_strething_down.setImageResource(R.drawable.hx_icon_10);
                showListView(1);
                break;
            case R.id.makePlan_iv_mainActionAdd:
            case R.id.base_day_rl_mainAction:
                iv_main_down.setImageResource(R.drawable.hx_icon_10);
                showListView(2);
                break;
            case R.id.makePlan_iv_relaxActionAdd:
            case R.id.base_day_rl_relaxAction:
                iv_relax_down.setImageResource(R.drawable.hx_icon_10);
                showListView(3);
                break;
            case R.id.iv_scanner_titlebar:
                if (EasyPermissions.hasPermissions(getActivity(), Manifest.permission.CAMERA)){
                    Intent intent = new Intent(getActivity(), ScannerActivity.class);
                    startActivityForResult(intent,123);
                }
                break;
        }
    }

    public void showListView(int i){
        if (show[i]){
            listViews[i].setVisibility(View.VISIBLE);
            show[i] = false;
        }else {
            listViews[i].setVisibility(View.GONE);

            show[i] = true;
        }
    }
}
