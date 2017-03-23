package com.sibo.fastsport.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sibo.fastsport.application.MyApplication;
import com.sibo.fastsport.model.UserInfo;

/**
 * Created by Administrator on 2016/10/24.
 */
public abstract class BaseFragment extends Fragment {
    private View rootView;
    private Context context;
    private Boolean hasInitData = false;
    protected UserInfo loginuser;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        context = getActivity();
        loginuser = MyApplication.getInstance().readLoginUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (rootView == null) {
            rootView = initView(inflater, container);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        if (!hasInitData) {
            initData();
            hasInitData = true;
        }
    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();
        ((ViewGroup) rootView.getParent()).removeView(rootView);
    }

    protected abstract void initData();

    protected abstract View initView(LayoutInflater inflater, ViewGroup container);
    /**
     * 封装从网络下载数据
     */

}

