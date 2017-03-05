package com.sibo.fastsport.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sibo.fastsport.R;
import com.sibo.fastsport.base.BaseActivity;

public class EditMyInfoActivity extends BaseActivity implements View.OnClickListener {
    EditText name, sex, telephone, age, height, weight, jiaoling, address, clubName;
    RelativeLayout jiaolingRl;
    ImageView ivBackTitle, ivCloseTitle;
    TextView tvNameTitle, tvComplete;
    //固定的ToolBar
    android.support.v7.widget.Toolbar rootToolBar;

    @Override
    protected void findViewByIDS() {
        name = (EditText) findViewById(R.id.edit_myinfo_et_name);
        sex = (EditText) findViewById(R.id.edit_myinfo_et_sex);
        telephone = (EditText) findViewById(R.id.edit_myinfo_et_telephone);
        age = (EditText) findViewById(R.id.edit_myinfo_et_age);
        height = (EditText) findViewById(R.id.edit_myinfo_et_height);
        weight = (EditText) findViewById(R.id.edit_myinfo_et_weight);
        jiaoling = (EditText) findViewById(R.id.edit_myinfo_et_jiaoling);
        address = (EditText) findViewById(R.id.edit_myinfo_et_address);
        clubName = (EditText) findViewById(R.id.edit_myinfo_et_club);
        jiaolingRl = (RelativeLayout) findViewById(R.id.edit_myinfo_rl_jiaoling);
        rootToolBar = (Toolbar) findViewById(R.id.edit_myinfo_title);
        ivBackTitle = (ImageView) rootToolBar.findViewById(R.id.iv_back_titlebar);
        ivCloseTitle = (ImageView) rootToolBar.findViewById(R.id.iv_close_titlebar);
        tvNameTitle = (TextView) rootToolBar.findViewById(R.id.tv_title_bar);
        tvComplete = (TextView) rootToolBar.findViewById(R.id.tv_complete_titlebar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmyinfo);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_myinfo_et_sex:
                break;
            case R.id.edit_myinfo_et_age:
                break;
            case R.id.edit_myinfo_et_height:
                break;
            case R.id.edit_myinfo_et_weight:
                break;
            case R.id.edit_myinfo_et_jiaoling:
                break;
            case R.id.edit_myinfo_et_address:
                break;

        }
    }
}
