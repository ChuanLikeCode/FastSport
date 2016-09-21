package com.sibo.fastsport;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
public class Register extends AppCompatActivity{
    Button register;
    TextView sendIndentify,receiverSecond,remainSecond,resetIndentify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = (Button) findViewById(R.id.btn_register);
        sendIndentify = (TextView) findViewById(R.id.tv_sendIdentify);
        receiverSecond = (TextView) findViewById(R.id.tv_receiverSecond);
        remainSecond = (TextView) findViewById(R.id.tv_remainSecond);
        resetIndentify = (TextView) findViewById(R.id.tv_resetIdentify);
        sendIndentify.setVisibility(View.GONE);
        receiverSecond.setVisibility(View.GONE);
        remainSecond.setVisibility(View.GONE);
        resetIndentify.setVisibility(View.GONE);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIndentify.setVisibility(View.VISIBLE);
                receiverSecond.setVisibility(View.VISIBLE);
                remainSecond.setVisibility(View.VISIBLE);
                resetIndentify.setVisibility(View.VISIBLE);
            }
        });
    }
}
