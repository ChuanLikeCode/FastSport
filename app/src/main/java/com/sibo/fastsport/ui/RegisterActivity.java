package com.sibo.fastsport.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sibo.fastsport.R;
import com.sibo.fastsport.utils.MyBombUtils;
import com.sibo.fastsport.view.WhorlView;

import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 *
 * Created by Administrator on 2016/9/21 0021.
 */
public class RegisterActivity extends BaseTranslucentActivity implements View.OnClickListener {
    private static final int CODE_ING = 1;   //已发送，倒计时
    private static final int CODE_REPEAT = 2;  //重新发送
    private static final int SMSDDK_HANDLER = 3;  //短信回调
    private static final int REGISTER_SUCCESS = 200;  //用户名不存在
    private static final int REGISTER_FAILED = 404;  //用户名已存在
    //固定的ToolBar
    private Toolbar rootToolBar;
    private ImageView ivClose;
    private ImageView ivBack;
    private TextView title;
    private TextView complete;
    private Button takeIndentify;
    private Button btn_register;//注册按钮
    private Button takeIdentify;//获取验证码按钮
    private TextView sendIdentify, receiverSecond, remainSecond;
    private EditText account;
    private ImageView ivDelete;
    //输入密码
    private EditText password;
    //输入验证码
    private EditText identify;
    private int TIME = 60;//倒计时60s
    private EventHandler eventHandler;

    private MyBombUtils myBmobUtils;
    private String userPhone;//用户注册的手机号
    private String userPassword;//用户密码
    private String userIdentify;//用户的验证码
    private Dialog dialog;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REGISTER_SUCCESS:
                    sendSMS();
                    break;
                case REGISTER_FAILED:
                    Toast.makeText(RegisterActivity.this, "用户名已被注册", Toast.LENGTH_LONG).show();
                    break;
                case CODE_ING://已发送，倒计时
                    remainSecond.setText(TIME + "S");
                    break;
                case CODE_REPEAT://重新发送
                    takeIdentify.setBackgroundResource(R.drawable.login_btn_background);
                    takeIdentify.setClickable(true);
                    break;
                case SMSDDK_HANDLER:
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    //回调完成
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        //验证码验证成功
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                            takeIdentify.setText(R.string.identifySuccess);
                            takeIdentify.setBackgroundResource(R.drawable.register_btn_selected);
                            takeIdentify.setClickable(false);
                            userPassword = password.getText().toString();
                            myBmobUtils.addUser(userPhone, userPassword);
                            dialog.dismiss();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        }
                        //已发送验证码
                        else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            Toast.makeText(RegisterActivity.this, "验证码已经发送",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        dialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "验证码错误请重新输入！",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    private int screen_height;
    private int screen_width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getScreenWH();
        initTitle();
        initView();
        initListener();
        initData();
        initSMS();
        initDialog();

    }

    private void getScreenWH() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screen_height = metrics.heightPixels;
        screen_width = metrics.widthPixels;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    /**
     * 登录时的loading对话框
     */
    private void initDialog() {
        dialog = new Dialog(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_view, null);
        WhorlView whorlView = (WhorlView) view.findViewById(R.id.loading);
        TextView register = (TextView) view.findViewById(R.id.dialog_tv);
        register.setText(R.string.register_loading);
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

    /**
     * 初始化短信注册回调方法
     * 获得验证码时会调用这个方法
     * 注册时也会调用这个方法
     */
    private void initSMS() {
        eventHandler = new EventHandler() {
            /**
             * EVENT	                                  DATA类型	                          说明
             * EVENT_GET_SUPPORTED_COUNTRIES	ArrayList<HashMap<String,Object>>	返回支持发送验证码的国家列表
             * EVENT_GET_VERIFICATION_CODE	    Boolean	                            true为智能验证，false为普通下发短信
             * EVENT_SUBMIT_VERIFICATION_CODE	HashMap<String,Object>	校验验证码，返回校验的手机和国家代码
             * EVENT_GET_CONTACTS	            ArrayList<HashMap<String,Object>>	获取手机内部的通信录列表
             * EVENT_SUBMIT_USER_INFO	                null	                    提交应用内的用户资料
             * EVENT_GET_FRIENDS_IN_APP	        ArrayList<HashMap<String,Object>>	获取手机通信录在当前应用内的用户列表
             * EVENT_GET_VOICE_VERIFICATION_CODE	    null	                    请求发送语音验证码，无返回
             * afterEvent在操作结束时被触发，同样具备event和data参数，但是data是事件操作结果
             *
             * @param event  事件返回的类型
             * @param result 事件返回的结果
             * @param data   事件执行结束返回的操作结果
             */
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message message = new Message();
                message.arg1 = event;
                message.arg2 = result;
                message.obj = data;
                message.what = SMSDDK_HANDLER;
                handler.sendMessage(message);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);//短信回调注册
    }

    private void initData() {
        title.setText(R.string.Register);
        ivClose.setVisibility(View.GONE);
        complete.setVisibility(View.GONE);
        setIdentifyVisibility(View.GONE);
        myBmobUtils = new MyBombUtils(this);
    }

    private void initListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        takeIdentify.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        ivDelete.setOnClickListener(this);
    }

    private void initTitle() {
        rootToolBar = (Toolbar) findViewById(R.id.register_title);
        ivClose = (ImageView) rootToolBar.findViewById(R.id.iv_close_titlebar);
        ivBack = (ImageView) rootToolBar.findViewById(R.id.iv_back_titlebar);
        title = (TextView) rootToolBar.findViewById(R.id.tv_title_bar);
        complete = (TextView) rootToolBar.findViewById(R.id.tv_complete_titlebar);
        setOrChangeTranslucentColor(rootToolBar, null, getResources().getColor(R.color.title));
    }

    private void initView() {

        btn_register = (Button) findViewById(R.id.btn_register);
        takeIdentify = (Button) findViewById(R.id.takeIndentify);
        sendIdentify = (TextView) findViewById(R.id.tv_sendIdentify);
        receiverSecond = (TextView) findViewById(R.id.tv_receiverSecond);
        remainSecond = (TextView) findViewById(R.id.tv_remainSecond);
        account = (EditText) findViewById(R.id.register_et_account);
        password = (EditText) findViewById(R.id.register_et_password);
        identify = (EditText) findViewById(R.id.et_identify);
        ivDelete = (ImageView) findViewById(R.id.cancel_number);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.takeIndentify:
                prepareSendSMS();
                break;
            case R.id.btn_register:
                dialog.show();
                userPhone = account.getText().toString();
                userPassword = password.getText().toString();
                userIdentify = identify.getText().toString();
                if (!(userPhone.equals("") || userPassword.equals(""))) {
                    if (!userIdentify.equals("")) {
                        SMSSDK.submitVerificationCode("86", userPhone, userIdentify);
                    } else {
                        dialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "验证码不能为空",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "用户名或者密码不能为空",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 短信验证提示文字的显示与隐藏
     *
     * @param visibility
     */
    private void setIdentifyVisibility(int visibility) {
        sendIdentify.setVisibility(visibility);
        receiverSecond.setVisibility(visibility);
        remainSecond.setVisibility(visibility);
    }

    /**
     * 验证手机号与密码（6-16位数字字母）
     *
     * @return
     */
    public boolean check() {
        //验证手机号码与密码（6-16数字字母）
        String regPhone = "^1[3|4|5|7|8][0-9]\\d{8}$";
        String regPassword = "^[0-9a-zA-z]{6,16}";
        //Log.e("userPhone",userPhone);
        //Log.e("userPassword",userPassword+"");
        boolean phoneBoolean = Pattern.matches(regPhone, userPhone);
        boolean pswBoolean = Pattern.matches(regPassword, userPassword);
        if (phoneBoolean && pswBoolean) {
            return true;
        }
        return false;
    }

    /**
     * 发送手机验证码的处理
     */
    private void prepareSendSMS() {
        userPhone = account.getText().toString();
        userPassword = password.getText().toString();
        if (!(userPhone.equals("") || userPassword.equals(""))) {
            if (check()) {
                myBmobUtils.registerChecked(userPhone);
            } else {
                Toast.makeText(RegisterActivity.this, "用户名不对或者密码只能为数字/字母(6-16位)",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(RegisterActivity.this, "用户名或者密码不能为空",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void sendSMS() {
        SMSSDK.getVerificationCode("86", userPhone);
        sendIdentify.setText("验证码已发送到" + userPhone);
        takeIdentify.setClickable(false);
        takeIdentify.setBackgroundResource(R.drawable.register_btn_selected);
        setIdentifyVisibility(View.VISIBLE);
        identifyCountDown();
    }

    /**
     * 验证码倒计时
     */
    public void identifyCountDown() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 60; i > 0; i--) {
                    TIME--;
                    handler.sendEmptyMessage(CODE_ING);
                    if (i <= 0) {
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.sendEmptyMessage(CODE_REPEAT);
            }
        }).start();
    }
}
