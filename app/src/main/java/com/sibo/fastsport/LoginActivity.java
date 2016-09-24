package com.sibo.fastsport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvRegister;
    android.support.v7.widget.Toolbar loginTitle;
    private ImageView ivClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();



    }

    private void initView() {

        loginTitle = (Toolbar) findViewById(R.id.title);
        tvRegister = (TextView) findViewById(R.id.tv_register);


        ivClose = (ImageView) loginTitle.findViewById(R.id.iv_close_titlebar);
        tvRegister.setOnClickListener(this);
        // ivClose.setOnClickListener(this);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });*/
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;

        }
    }
}
