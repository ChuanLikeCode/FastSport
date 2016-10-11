package com.sibo.fastsport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sibo.fastsport.R;
import com.sibo.fastsport.recyclerview.StudentRecyclerView;
import com.sibo.fastsport.widgets.MetaballMenu;


public class StudentActivity extends AppCompatActivity implements View.OnClickListener, MetaballMenu.MetaballMenuClickListener {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RecyclerView.Adapter adapter;
    android.support.v7.widget.Toolbar studentTitle;

    MetaballMenu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        initView();
    }

    private void initView() {
        menu = (MetaballMenu) findViewById(R.id.student_meun);
        menu.setMenuClickListener(this);

        studentTitle = (Toolbar) findViewById(R.id.student_title_bar);
        recyclerView = (RecyclerView) findViewById(R.id.student_recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new StudentRecyclerView(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menuPlan:

                startActivity(new Intent(StudentActivity.this, MakePlanActivity.class));
                break;
            case R.id.menuMyhome:

                startActivity(new Intent(StudentActivity.this, MyHomeActivity.class));
                break;
        }
    }
}
