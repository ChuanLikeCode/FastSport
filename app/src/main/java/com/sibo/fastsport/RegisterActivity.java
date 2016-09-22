package com.sibo.fastsport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
public class RegisterActivity extends AppCompatActivity {
    Button takeIndentify;
    ImageView back;
    TextView sendIndentify,receiverSecond,remainSecond,resetIndentify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        back = (ImageView) findViewById(R.id.iv_back);
        takeIndentify = (Button) findViewById(R.id.takeIndentify);
        sendIndentify = (TextView) findViewById(R.id.tv_sendIdentify);
        receiverSecond = (TextView) findViewById(R.id.tv_receiverSecond);
        remainSecond = (TextView) findViewById(R.id.tv_remainSecond);
        resetIndentify = (TextView) findViewById(R.id.tv_resetIdentify);
        sendIndentify.setVisibility(View.GONE);
        receiverSecond.setVisibility(View.GONE);
        remainSecond.setVisibility(View.GONE);
        resetIndentify.setVisibility(View.GONE);
        takeIndentify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIndentify.setVisibility(View.VISIBLE);
                receiverSecond.setVisibility(View.VISIBLE);
                remainSecond.setVisibility(View.VISIBLE);
                resetIndentify.setVisibility(View.VISIBLE);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}
