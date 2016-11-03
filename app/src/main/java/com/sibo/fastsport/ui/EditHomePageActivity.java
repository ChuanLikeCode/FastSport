package com.sibo.fastsport.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.sibo.fastsport.R;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class EditHomePageActivity extends Activity implements View.OnClickListener {
    private ImageView back;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editzhuye);

        initView();

    }

    private void initView() {

        back = (ImageView) findViewById(R.id.activity_editzhuye_iv_back);
        recyclerView = (RecyclerView) findViewById(R.id.activity_editzhuye_recycler_view);
        back.setOnClickListener(this);
        recyclerView.setVisibility(View.GONE);

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
