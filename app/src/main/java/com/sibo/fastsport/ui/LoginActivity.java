package com.sibo.fastsport.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sibo.fastsport.R;
import com.sibo.fastsport.application.Constant;
import com.sibo.fastsport.application.MyApplication;
import com.sibo.fastsport.utils.MyBombUtils;
import com.sibo.fastsport.utils.SharepreferencesUtilSystemSettings;
import com.sibo.fastsport.view.WhorlView;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
public class LoginActivity extends BaseTranslucentActivity implements View.OnClickListener {

    //固定的ToolBar
    private Toolbar rootToolBar;
    private ImageView ivClose;
    private ImageView ivBack;
    private TextView title;
    private TextView complete;

    //注册按钮
    private TextView tvToRegister;
    //账号
    private EditText userAccount;
    //密码
    private EditText userPassWord;
    //登录按钮
    private Button btn_login;
    private MyBombUtils myBombUtils;

    private MyApplication myApplication;

    private Dialog dialog;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //Log.e("handleMessage",msg.what+"");
            if (msg.what == 0) {
                if (MyBombUtils.identifySuccess) {
                    //Log.e("identifySuccess",MyBmobUtils.identifySuccess+"");
                    dialog.dismiss();
                    Log.e("mAcount",MyApplication.mAccount+"");
                    MyApplication.mAccount.setAccount(userAccount.getText().toString());
                    MyApplication.mAccount.setPassword(userPassWord.getText().toString());
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, ChooseActivity.class);
                    startActivity(intent);
                    MyApplication.isLogin = false;
                    MyApplication.mUser.setAccount(userAccount.getText().toString());
                    SharepreferencesUtilSystemSettings.putValue(LoginActivity.this,
                            Constant.USERACCOUNTCOOKIE, MyApplication.mUser.getAccount());
                    SharepreferencesUtilSystemSettings.putValue(LoginActivity.this,
                            Constant.USERACCOUNTCOOKIE, MyApplication.mAccount.getAccount());
                    SharepreferencesUtilSystemSettings.putValue(LoginActivity.this,
                            Constant.USERPASSWORDCOOKIE, MyApplication.mAccount.getPassword());
                    SharepreferencesUtilSystemSettings.putValue(LoginActivity.this,
                            Constant.ISLOGIN, MyApplication.isLogin);
                    finish();
                } else {
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, "用户名或者密码错误", Toast.LENGTH_LONG).show();
                    userPassWord.setText("");
                }
            }

        }
    };
    private int screen_height;
    private int screen_width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkIsFirstLogin();
        getScreenWH();
        setContentView(R.layout.activity_login);
        initView();
        initTitle();
        initData();
        initListener();
        initDialog();

    }

    private void getScreenWH() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screen_height = metrics.heightPixels;
        screen_width = metrics.widthPixels;
    }

    private void initDialog() {
        dialog = new Dialog(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_view, null);
        WhorlView whorlView = (WhorlView) view.findViewById(R.id.loading);
        whorlView.start();
        Window window = dialog.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.getDecorView().setPadding(50, 50, 50, 50);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = screen_width / 2;
        layoutParams.height = 3 * screen_height / 8;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view, layoutParams);
    }

    private void checkIsFirstLogin() {
        //myApplication = (MyApplication) getApplication();
        Log.e("checkLoginisLogin", MyApplication.isLogin + "");
        if (!MyApplication.isLogin) {
            startActivity(new Intent(this, ChooseActivity.class));
            finish();
        }
    }

    private void initData() {
        title.setText(R.string.Login);
        //隐藏顶部栏不需要的控件
        ivBack.setVisibility(View.GONE);
        ivClose.setVisibility(View.GONE);
        complete.setVisibility(View.GONE);
        myBombUtils = new MyBombUtils(this);
    }

    private void initListener() {
        tvToRegister.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    private void initTitle() {
        rootToolBar = (Toolbar) findViewById(R.id.login_title);
        ivClose = (ImageView) rootToolBar.findViewById(R.id.iv_close_titlebar);
        ivBack = (ImageView) rootToolBar.findViewById(R.id.iv_back_titlebar);
        title = (TextView) rootToolBar.findViewById(R.id.tv_title_bar);
        complete = (TextView) rootToolBar.findViewById(R.id.tv_complete_titlebar);
        setOrChangeTranslucentColor(rootToolBar, null, getResources().getColor(R.color.title));
    }

    private void initView() {
        tvToRegister = (TextView) findViewById(R.id.tv_register);
        btn_login = (Button) findViewById(R.id.btn_login);
        userAccount = (EditText) findViewById(R.id.login_et_account);
        userPassWord = (EditText) findViewById(R.id.login_et_password);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:

                checkAccountAndPassword();
                break;
        }

    }

    private void checkAccountAndPassword() {
        String account = userAccount.getText().toString();
        String password = userPassWord.getText().toString();
        if (account.equals("") || password.equals("")) {
            Toast.makeText(LoginActivity.this, "请输入用户名或密码", Toast.LENGTH_LONG).show();
        } else {
            dialog.show();
            myBombUtils.queryAccount(account, password);
        }
    }
}
