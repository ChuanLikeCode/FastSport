package com.sibo.fastsport.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.sibo.fastsport.model.Account;
import com.sibo.fastsport.model.UserInfo;
import com.sibo.fastsport.utils.AppManager;
import com.sibo.fastsport.utils.SharepreferencesUtilSystemSettings;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import cn.bmob.v3.Bmob;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2016/11/19.
 */
public class MyApp extends Application {

    public static Account mAccount = null;
    public static UserInfo mUser = null;
    public static boolean isLogin = true;
    public static String planObjectId = "";
    private static MyApp mInstance = null;
    public boolean isFirstStart = true;
    private AppManager mAppManager = null;

    public static MyApp getInstance() {
        return mInstance;
    }
    /**
     * 获得当前进程号
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        /*DeadObjectException 异常出现，一般原因
        app进程不存在，在底层回调时找不到callback
        ipc进程崩溃也会出现改异常
        遇到该异常时，首先要检查app中的进程
        如果你的 app 有多进程，比如除了com.app进程外，你还有com.app.remote，
        那么在 RongIM.init 时，除了主进程其他进程不要做初始化，即在if(getPid() != “com.app.remote”)后再作init().
        sdk底层有ipc进程和push进程，每个进程在启动创建时，都会走一次Application的onCreate()，
        所以在RongIM.init()初始化后，消息注册等注册相关的逻辑前，要加上进程判断，只允许主进程做这些注册。*/
//        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
//                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {
//            RongIM.init(this);

        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
            ZXingLibrary.initDisplayOpinion(this);
            SMSSDK.initSDK(this, "193141f4621c1", "b393e87172c18c5069feaf2a4286bc15");
            Bmob.initialize(this, "f79d34f38040f7e7512a4228ea4d0c7a");
            initLoginParams();
            updateLoginParams(mAccount, mUser);
//                implementUserInfoProvider();
//
//                InputProvider.ExtendProvider[] provider = {
//                        new ImageInputProvider(RongContext.getInstance()),//图片
//                        new CameraInputProvider(RongContext.getInstance()),//相机
//                };
//
//                RongIM.getInstance().resetInputExtensionProvider(Conversation.ConversationType.PRIVATE, provider);
        }
    }

    private void updateLoginParams(Account account, UserInfo user) {
        mAccount = account;
        mUser = user;
        SharepreferencesUtilSystemSettings.putValue(this, Constant.USERACCOUNTCOOKIE, account.getAccount());
        SharepreferencesUtilSystemSettings.putValue(this, Constant.USERPASSWORDCOOKIE, account.getPassword());
        SharepreferencesUtilSystemSettings.putValue(this, Constant.ISLOGIN, isLogin);
    }

    private void initLoginParams() {
        String userAccount = SharepreferencesUtilSystemSettings.getValue(this, Constant.USERACCOUNTCOOKIE, "");
        String userPassword = SharepreferencesUtilSystemSettings.getValue(this, Constant.USERPASSWORDCOOKIE, "");
        isFirstStart = SharepreferencesUtilSystemSettings.getValue(this, Constant.ISFIRSTSTART, true);
        isLogin = SharepreferencesUtilSystemSettings.getValue(this, Constant.ISLOGIN, true);
        planObjectId = SharepreferencesUtilSystemSettings.getValue(this,Constant.PLANOBJECTID,"");
        String type = SharepreferencesUtilSystemSettings.getValue(this,Constant.USER_TYPE,null);
        mUser = new UserInfo();
        mAccount = new Account();
        mAccount.setAccount(userAccount);
        mAccount.setPassword(userPassword);
        mUser.setPlanObjectId(planObjectId);
        mUser.setType(type);
        mUser.setAccount(userAccount);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        System.exit(0);
    }

    /**
     * activity栈管理
     *
     * @author chensong
     * @date 2016-5-25
     */
    public AppManager getActivityManager() {
        if (mAppManager == null) {
            mAppManager = AppManager.getInstance();
        }
        return mAppManager;
    }

}
