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
 * Created by Administrator on 2016/7/26 0026.
 */
public class PersonalHome extends Activity implements View.OnClickListener{
    private ImageView back;
    private ImageView editHome;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalhome);
        recyclerView = (RecyclerView)findViewById(R.id.activity_personalhome_recycler_view);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new DetailsRecyclerView(this);
        recyclerView.setAdapter(adapter);
        back = (ImageView)findViewById(R.id.activity_personalhome_iv_back);
        editHome = (ImageView)findViewById(R.id.activity_personalhome_iv_touxiang);
        editHome.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_personalhome_iv_back:
                Intent mainIntent = new Intent(PersonalHome.this,MainActivity.class);
                startActivity(mainIntent);
                finish();
                break;
            case R.id.activity_personalhome_iv_touxiang:
                Intent editHomeIntent = new Intent(PersonalHome.this,EditHomePage.class);
                startActivity(editHomeIntent);
                finish();
        }

    }
}
