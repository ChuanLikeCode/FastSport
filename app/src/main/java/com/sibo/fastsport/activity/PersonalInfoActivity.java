package com.sibo.fastsport.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibo.fastsport.R;
import com.sibo.fastsport.recyclerview.PersonalInfoRecyclerView;

/**
 * Created by Administrator on 2016/7/25 0025.
 */
public class PersonalInfoActivity extends Activity implements View.OnClickListener{
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RecyclerView.Adapter adapter;
    android.support.v7.widget.Toolbar loginTitle;
    private ImageView ivBack;
    private ImageView ivClose;
    private TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);

        initView();


    }

    private void initView() {
        loginTitle = (Toolbar) findViewById(R.id.title);
        ivBack = (ImageView) loginTitle.findViewById(R.id.iv_back_titlebar);
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.activity_personalinfo_recycler_view);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager  = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new PersonalInfoRecyclerView(this);
        recyclerView.setAdapter(adapter);


        tvInfo = (TextView) loginTitle.findViewById(R.id.tv_title_bar);
        ivClose = (ImageView) loginTitle.findViewById(R.id.iv_close_titlebar);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvInfo.setText("个人信息");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back_titlebar) {
            Intent mainIntent = new Intent(PersonalInfoActivity.this,MainActivity.class);
            startActivity(mainIntent);
            finish();
        }
    }
}
