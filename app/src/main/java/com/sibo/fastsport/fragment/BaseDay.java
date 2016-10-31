package com.sibo.fastsport.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibo.fastsport.R;

/**
 * Created by Administrator on 2016/10/28.
 */
public class BaseDay extends BaseFragment {
    public View dayView;
    public View warmUpView, stretchingView, mainActionView, relaxActionView;
    public TextView tv_warmUp, tv_stretching, tv_mainAction, tv_relaxAction, tips;
    public ImageView warmUpAdd, stretchingAdd, mainActionAdd, relaxActionAdd;
    public RecyclerView warmUpRecyclerView, stretchingRecyclerView, mainActionRecyclerView, relaxActionRecyclerView;
    private View view;//主界面布局

    @Override
    protected void initData() {
        tv_warmUp.setText(R.string.warm_up);
        tv_stretching.setText(R.string.stretching);
        tv_mainAction.setText(R.string.main_action);
        tv_relaxAction.setText(R.string.relax_action);
        warmUpView.setVisibility(View.GONE);
        stretchingView.setVisibility(View.GONE);
        mainActionView.setVisibility(View.GONE);
        relaxActionView.setVisibility(View.GONE);
    }

    @Override
    protected View initView(LayoutInflater inflater) {

        view = inflater.inflate(R.layout.fragment_day, null);
        findById();
        return view;
    }

    private void findById() {
        dayView = view.findViewById(R.id.day1);
        warmUpView = dayView.findViewById(R.id.day_warmUp);
        stretchingView = dayView.findViewById(R.id.day_stretching);
        mainActionView = dayView.findViewById(R.id.day_mainAction);
        relaxActionView = dayView.findViewById(R.id.day_relaxAction);
        tips = (TextView) dayView.findViewById(R.id.makePlan_tip);

        tv_warmUp = (TextView) warmUpView.findViewById(R.id.makePlan_tv);
        warmUpAdd = (ImageView) warmUpView.findViewById(R.id.makePlan_iv_Add);
        warmUpRecyclerView = (RecyclerView) warmUpView.findViewById(R.id.makePlan_recyclerView);

        tv_stretching = (TextView) stretchingView.findViewById(R.id.makePlan_tv);
        stretchingAdd = (ImageView) stretchingView.findViewById(R.id.makePlan_iv_Add);

        stretchingRecyclerView = (RecyclerView) stretchingView.findViewById(R.id.makePlan_recyclerView);

        tv_mainAction = (TextView) mainActionView.findViewById(R.id.makePlan_tv);
        mainActionAdd = (ImageView) mainActionView.findViewById(R.id.makePlan_iv_Add);
        mainActionRecyclerView = (RecyclerView) mainActionView.findViewById(R.id.makePlan_recyclerView);

        tv_relaxAction = (TextView) relaxActionView.findViewById(R.id.makePlan_tv);
        relaxActionAdd = (ImageView) relaxActionView.findViewById(R.id.makePlan_iv_Add);
        relaxActionRecyclerView = (RecyclerView) relaxActionView.findViewById(R.id.makePlan_recyclerView);
    }

    public View getWarmUpView() {
        return warmUpView;
    }
}
