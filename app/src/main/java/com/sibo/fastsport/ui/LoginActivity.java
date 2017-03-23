package com.sibo.fastsport.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sibo.fastsport.R;
import com.sibo.fastsport.application.Constant;
import com.sibo.fastsport.application.MyApplication;
import com.sibo.fastsport.base.BaseActivity;
import com.sibo.fastsport.model.UserInfo;
import com.sibo.fastsport.utils.MyBombUtils;
import com.sibo.fastsport.utils.SharepreferencesUtilSystemSettings;


/**
 * Created by Administrator on 2016/9/21 0021.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;
    public UserInfo account = new UserInfo();
    //注册按钮
    private TextView tvToRegister;
    //账号
    private EditText userAccount;
    //密码
    private EditText userPassWord;
    //登录按钮
    private Button btn_login;
    private MyBombUtils myBombUtils;
    private TextView forgetPassword;
    private String userPhone,userPassword;

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Constant.RESULT_SUCCESS){
                if (myBombUtils.loginSuccess){
                    dialog.dismiss();
                    myBombUtils.updateAccountInfo(account);
                    MyApplication.getInstance().saveUserInfo(account);
                    startActivity(MainActivity.class);
                    finish();
                }else{
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, "用户名或者密码错误", Toast.LENGTH_LONG).show();
                    userPassWord.setText("");
                }

            }
        }
    };

    @Override
    protected void findViewByIDS() {
        title = (TextView) findViewById(R.id.top_tv_title);
        tvToRegister = (TextView) findViewById(R.id.tv_register);
        btn_login = (Button) findViewById(R.id.btn_login);
        userAccount = (EditText) findViewById(R.id.login_et_account);
        userPassWord = (EditText) findViewById(R.id.login_et_password);
        forgetPassword = findViewsById(R.id.tv_resetPassword);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginuser = MyApplication.getInstance().readLoginUser();
        if (loginuser != null) {
            startActivity(MainActivity.class);
            finish();
            return;
        }
        setContentView(R.layout.activity_login);
        initData();
        initListener();
    }

    private void initData() {
        title.setText(R.string.Login);
        myBombUtils = new MyBombUtils(this);
    }

    private void initListener() {
        tvToRegister.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                intent.putExtra("type","register");
                startActivity(intent);
                break;
            case R.id.btn_login:
                checkAccountAndPassword();
                break;
            case R.id.tv_resetPassword:
                Intent intent1 = new Intent(LoginActivity.this,RegisterActivity.class);
                intent1.putExtra("type","find");
                startActivity(intent1);
                break;
        }

    }

    private void checkAccountAndPassword() {
        userPhone = userAccount.getText().toString();
        userPassword = userPassWord.getText().toString();
        account.setAccount(userPhone);
        account.setPassword(userPassword);
        if (userPhone.equals("") || userPassword.equals("")) {
            Toast.makeText(LoginActivity.this, "请输入用户名或密码", Toast.LENGTH_LONG).show();
        } else {
            dialog = ProgressDialog.show(this, null, "正在登录...");
            myBombUtils.queryAccount(account);
        }
    }
}
