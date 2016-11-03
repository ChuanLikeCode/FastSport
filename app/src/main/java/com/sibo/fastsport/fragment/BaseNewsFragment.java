package com.sibo.fastsport.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/11/02.
 */
public abstract class BaseNewsFragment extends Fragment {
    private View rootView;
    private Context context;
    private int index;
    private Boolean hasInitData = false;

    public BaseNewsFragment(int index) {
        this.index = index;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (rootView == null) {
            rootView = initView(inflater);
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

    protected abstract View initView(LayoutInflater inflater);
    /**
     * 封装从网络下载数据
     */

}
