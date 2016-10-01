package com.sibo.fastsport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.sibo.fastsport.DetailsRecyclerView;
import com.sibo.fastsport.R;
import com.sibo.fastsport.view.BaseTranslucentActivity;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class MyHomeActivity extends BaseTranslucentActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RecyclerView.Adapter adapter;
    private ImageView back;
    private ImageView editHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myhome);

        initView();

    }

    private void initView() {

        recyclerView = (RecyclerView) findViewById(R.id.activity_personalhome_recycler_view);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new DetailsRecyclerView(this);
        recyclerView.setAdapter(adapter);

        editHome = (ImageView) findViewById(R.id.activity_mylhome_iv_touxiang);
        editHome.setOnClickListener(this);


        setOrChangeTranslucentColor(null, getResources().getColor(R.color.turquoise));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){


            case R.id.activity_mylhome_iv_touxiang:
                Intent editHomeIntent = new Intent(MyHomeActivity.this, EditHomePageActivity.class);
                startActivity(editHomeIntent);
                finish();
        }

    }
}
