package com.sibo.fastsport.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.sibo.fastsport.R;
import com.sibo.fastsport.adapter.StudentRecyclerView;


public class StudentFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.Adapter adapter;
    private android.support.v7.widget.Toolbar studentTitle;
    private View studentFragment;

    @Override
    protected View initView(LayoutInflater inflater) {
        studentFragment = inflater.inflate(R.layout.fragment_student, null);
        initView();
        initData();
        return studentFragment;
    }

    @Override
    protected void initData() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new StudentRecyclerView(getActivity());
        recyclerView.setAdapter(adapter);
    }


    private void initView() {

        studentTitle = (Toolbar) studentFragment.findViewById(R.id.student_title_bar);
        recyclerView = (RecyclerView) studentFragment.findViewById(R.id.student_recycler_view);

    }

}
