package com.sibo.fastsport.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.sibo.fastsport.R;
import com.sibo.fastsport.base.BaseActivity;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class EditHomePageActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private RecyclerView recyclerView;

    @Override
    protected void findViewByIDS() {
        back = (ImageView) findViewById(R.id.activity_editzhuye_iv_back);
        recyclerView = (RecyclerView) findViewById(R.id.activity_editzhuye_recycler_view);
        back.setOnClickListener(this);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editzhuye);



    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.activity_editzhuye_iv_back) {
            Intent intent = new Intent(EditHomePageActivity.this, MyHomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
