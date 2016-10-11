package com.sibo.fastsport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sibo.fastsport.R;
import com.sibo.fastsport.widgets.MetaballMenu;

public class MakePlanActivity extends AppCompatActivity implements View.OnClickListener, MetaballMenu.MetaballMenuClickListener {
    MetaballMenu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeplan);
        initView();
    }

    private void initView() {
        menu = (MetaballMenu) findViewById(R.id.plan_menu);
        menu.setMenuClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menuStudent:

                startActivity(new Intent(MakePlanActivity.this, StudentActivity.class));
                break;
            case R.id.menuMyhome:

                startActivity(new Intent(MakePlanActivity.this, MyHomeActivity.class));
                break;
        }
    }
}
