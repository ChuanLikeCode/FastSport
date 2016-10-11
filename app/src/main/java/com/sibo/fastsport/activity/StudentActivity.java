package com.sibo.fastsport.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sibo.fastsport.R;
import com.sibo.fastsport.recyclerview.StudentRecyclerView;
import com.sibo.fastsport.widgets.MetaballMenu;


public class StudentActivity extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RecyclerView.Adapter adapter;
    android.support.v7.widget.Toolbar studentTitle;

    MetaballMenu menu;
    View studentFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        studentFragment = inflater.inflate(R.layout.activity_student, container, false);
        initView();
        return studentFragment;
    }

    private void initView() {

        studentTitle = (Toolbar) studentFragment.findViewById(R.id.student_title_bar);
        recyclerView = (RecyclerView) studentFragment.findViewById(R.id.student_recycler_view);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new StudentRecyclerView(getActivity());
        recyclerView.setAdapter(adapter);
    }

}
