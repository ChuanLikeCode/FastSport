package com.sibo.fastsport.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.sibo.fastsport.R;
import com.sibo.fastsport.application.Constant;
import com.sibo.fastsport.application.MyApplication;
import com.sibo.fastsport.utils.MyBombUtils;
import com.sibo.fastsport.utils.SharepreferencesUtilSystemSettings;

public class ChooseActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView trainer,student;
    private MyBombUtils bombUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏通知栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose);
        trainer = (TextView) findViewById(R.id.choose_FitnessTrainer);
        student = (TextView) findViewById(R.id.choose_student);
        trainer.setOnClickListener(this);
        student.setOnClickListener(this);
        bombUtils = new MyBombUtils(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ChooseActivity.this,MainActivity.class);
        switch (v.getId()){
            case R.id.choose_FitnessTrainer:
                SharepreferencesUtilSystemSettings.putValue(this, Constant.USER_TYPE,"1");
                intent.putExtra(Constant.USER_TYPE,"1");
                MyApplication.mUser.setType("1");
                bombUtils.updateUserInfo(MyApplication.mUser);
                break;
            case R.id.choose_student:
                SharepreferencesUtilSystemSettings.putValue(this, Constant.USER_TYPE,"2");
                intent.putExtra(Constant.USER_TYPE,"2");
                MyApplication.mUser.setType("2");
                bombUtils.updateUserInfo(MyApplication.mUser);
                break;
        }
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        String type = MyApplication.mUser.getType();
        //Log.e("type",type+"");
        if (type != null){
            startActivity(new Intent(ChooseActivity.this,MainActivity.class));
            finish();
        }
    }
}
