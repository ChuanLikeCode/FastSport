package com.sibo.fastsport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.sibo.fastsport.R;

public class BodyjudgmentActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    RelativeLayout bodyLable, bodyLabelByYourself;
    Switch selectBody_switch;
    Toolbar head;
    TextView title, zhiding;
    ImageView back, close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bodyjudgment);
        initView();
        initData();
        initListener();

    }

    private void initData() {
        title.setText(R.string.judgeBody);
        close.setVisibility(View.GONE);
        zhiding.setText(R.string.zhiding);
    }

    private void initListener() {
        selectBody_switch.setOnCheckedChangeListener(this);
        back.setOnClickListener(this);
        zhiding.setOnClickListener(this);
    }

    private void initView() {
        head = (Toolbar) findViewById(R.id.bodyTitle);
        title = (TextView) head.findViewById(R.id.tv_title_bar);
        back = (ImageView) head.findViewById(R.id.iv_back_titlebar);
        close = (ImageView) head.findViewById(R.id.iv_close_titlebar);

        zhiding = (TextView) head.findViewById(R.id.tv_complete_titlebar);
        selectBody_switch = (Switch) findViewById(R.id.selectBody_switch);
        bodyLabelByYourself = (RelativeLayout) findViewById(R.id.rl_bodyLabelByYourself);
        bodyLable = (RelativeLayout) findViewById(R.id.rl_bodyLabel);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_complete_titlebar:
                startActivity(new Intent(BodyjudgmentActivity.this, MakePlanActivity.class));
                break;
            case R.id.iv_back_titlebar:
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            bodyLable.setVisibility(View.VISIBLE);
            bodyLabelByYourself.setVisibility(View.VISIBLE);
        } else {
            bodyLabelByYourself.setVisibility(View.GONE);
            bodyLable.setVisibility(View.GONE);
        }
    }
}
