package com.sibo.fastsport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/7/25 0025.
 */
public class PersonalInfoActivity extends Activity implements View.OnClickListener{
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RecyclerView.Adapter adapter;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        back = (ImageView)findViewById(R.id.activity_myinfo_back);
        back.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.activity_personalinfo_recycler_view);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager  = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new PersonalInfoRecyclerView(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.activity_myinfo_back){
            Intent mainIntent = new Intent(PersonalInfoActivity.this,MainActivity.class);
            startActivity(mainIntent);
            finish();
        }
    }
}
