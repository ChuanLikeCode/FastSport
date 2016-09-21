package com.sibo.fastsport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/7/25 0025.
 */
public class SettingActivity extends Activity implements View.OnClickListener{
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        back = (ImageView)findViewById(R.id.activity_setting_back);
        back.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.activity_setting_back){
            Intent mainIntent = new Intent(SettingActivity.this,MainActivity.class);
            startActivity(mainIntent);
            finish();
        }
    }
}
