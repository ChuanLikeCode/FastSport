package com.sibo.fastsport.utils;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.sibo.fastsport.application.Constant;
import com.sibo.fastsport.application.MyApplication;
import com.sibo.fastsport.domain.MyCollections;
import com.sibo.fastsport.domain.SportDetail;
import com.sibo.fastsport.domain.SportName;
import com.sibo.fastsport.model.Account;
import com.sibo.fastsport.model.DayPlan;
import com.sibo.fastsport.model.MainAction;
import com.sibo.fastsport.model.RelaxAction;
import com.sibo.fastsport.model.Stretching;
import com.sibo.fastsport.model.UserSportPlan;
import com.sibo.fastsport.model.WarmUp;
import com.sibo.fastsport.ui.ChooseActionActivity;
import com.sibo.fastsport.ui.LoginActivity;
import com.sibo.fastsport.ui.MakePlanActivity;
import com.sibo.fastsport.ui.RegisterActivity;
import com.sibo.fastsport.ui.WxCollectedActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


/**
 * Bomb服务器的工具类
 * Created by Administrator on 2016/10/31.
 */
public class MyBombUtils {

    public static int  ADD_PLAN = 0;
    public static int  ADD_WARM = 0;
    public static int  ADD_STRE = 0;
    public static int  ADD_MAIN = 0;
    public static int  ADD_RELAX = 0;

    private int plan = 0;
    private int warmUp = 0;
    private int stre = 0;
    private int mainAction = 0;
    private int relaxAction = 0;


    private final static int SPORT_NAME_FINISH = 1;
    private final static int SPORT_DETAIL_FINISH = 2;

    public static List<SportName> list_sportName = new ArrayList<SportName>();
    public static List<SportDetail> list_sportDetail = new ArrayList<SportDetail>();
    public static boolean isFirst = true;

    public static boolean identifySuccess = false;
    public Context context;
    private boolean registerSuccess = true;

    public MyBombUtils(Context context) {
        this.context = context;
    }

    /**
     * 从bmob获取收藏的文章,以后的修改方案，加入本地数据库中，速度更快
     */
    public void queryCollection(){
        BmobQuery<MyCollections> query = new BmobQuery<>();
        query.findObjects(new FindListener<MyCollections>() {
            @Override
            public void done(List<MyCollections> list, BmobException e) {
                WxCollectedActivity.collectionList.clear();
                for (MyCollections m:
                        list) {
                    if (m.getAccount().equals(MyApplication.mAccount.getAccount())){
                        WxCollectedActivity.collectionList.add(m);
                    }
                }
                ((WxCollectedActivity)context).handler.sendEmptyMessage(Constant.SUCCESS);
            }
        });
    }
    /**
     * 增加收藏文章
     * @param list
     */
    public void addCollection(List<MyCollections> list){
        for (MyCollections m :
                list) {
            m.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null){
                        Log.e("addCollection",s);
                    }else {
                        Log.e("addCollection","failed");
                    }
                }
            });
        }
    }

    /**
     * 增加健身计划名字
     * @param userSportPlan
     */
    public void addPlan(UserSportPlan userSportPlan){

        userSportPlan.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null){
                    CollectPlan.id = s;
                    CollectPlan.prepareToPush();
                    addDayPlan();
                    Log.e("addPlan",s);
                    ((MakePlanActivity)context).handler2.sendEmptyMessage(Constant.SHOW);
                }else {
                    e.printStackTrace();
                    Log.e("addPlan","failed");
                }
            }
        });
    }



    /**
     * 增加计划列表DayPlan
     */
    public void addDayPlan(){
        for (DayPlan d : CollectPlan.dayPlan){
            d.setId(CollectPlan.id);
            //plan++;
            d.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null){
                        Log.e("addDayPlan",s);

                    }else {
                        Log.e("addDayPlan","failed");
                    }
                }
            });
        }
    }

    public void addWarmUp(){
        Log.e("Bmobsp_warmUp",CollectPlan.warmUps.size()+"");
        if (CollectPlan.warmUps.size()!=0){
            for (WarmUp w : CollectPlan.warmUps){
               // warmUp++;
                w.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null){
                            Log.e("addWarmUp",s);
//                            if ((plan == ADD_PLAN )&&(warmUp == ADD_WARM )
//                                    &&(stre == ADD_STRE )&&(mainAction == ADD_MAIN )
//                                    &&(relaxAction == ADD_RELAX )){
//                                Log.e("addWarmUpupload","全部上传完成");
//                                ((MakePlanActivity)context).handler.sendEmptyMessage(Constant.SUCCESS);
//                            }
                        }else {
                            Log.e("addWarmUp","failed");
                        }
                    }
                });
            }
        }

    }
    public void addStretching(){
        Log.e("addStretching",CollectPlan.stretchings.size()+"");
        if (CollectPlan.stretchings.size() != 0){
            for (Stretching s : CollectPlan.stretchings){
                //stre++;
                s.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null){
                            Log.e("addStretching",s);
//                            if ((plan == ADD_PLAN )&&(warmUp == ADD_WARM )
//                                    &&(stre == ADD_STRE )&&(mainAction == ADD_MAIN )
//                                    &&(relaxAction == ADD_RELAX )){
//                                Log.e("addStretchingupload","全部上传完成");
//                                ((MakePlanActivity)context).handler.sendEmptyMessage(Constant.SUCCESS);
//                            }
                        }else {
                            Log.e("addStretching","failed");
                        }
                    }
                });
            }
        }

    }
    public void addMainAction(){
        Log.e("addMainAction",CollectPlan.mainActions.size()+"");
        if (CollectPlan.mainActions.size() != 0){
            for (MainAction m : CollectPlan.mainActions){
                //mainAction++;
                m.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null){
                            Log.e("addMainAction",s);
//                            if ((plan == ADD_PLAN )&&(warmUp == ADD_WARM )
//                                    &&(stre == ADD_STRE )&&(mainAction == ADD_MAIN )
//                                    &&(relaxAction == ADD_RELAX )){
//                                Log.e("addMainActionupload","全部上传完成");
//                                ((MakePlanActivity)context).handler.sendEmptyMessage(Constant.SUCCESS);
//                            }
                        }else {
                            Log.e("addMainAction","failed");
                        }
                    }
                });
            }
        }

    }
    public void addRelaxAction(){
        Log.e("addRelaxAction",CollectPlan.relaxActions.size()+"");
        if (CollectPlan.relaxActions.size() != 0){
            for (RelaxAction r : CollectPlan.relaxActions){
                //relaxAction++;
                r.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null){
                            //((MakePlanActivity)context).handler.sendEmptyMessage(Constant.SUCCESS);
                            Log.e("addRelaxAction",s);
//                            if ((plan == ADD_PLAN )&&(warmUp == ADD_WARM )
//                                    &&(stre == ADD_STRE )&&(mainAction == ADD_MAIN )
//                                    &&(relaxAction == ADD_RELAX )){
//                                Log.e("addRelaxActionupload","全部上传完成");
//                                ((MakePlanActivity)context).handler.sendEmptyMessage(Constant.SUCCESS);
//                            }
                        }else {
                            Log.e("addRelaxAction","failed");
                        }
                    }
                });
            }
        }

    }

    /**
     * 增加新用户
     * @param userPhone 账号
     * @param password 密码
     */
    public void addUser(String userPhone, String password) {
        Account account = new Account();
        account.setAccount(userPhone);
        account.setPassword(password);
        account.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Log.e("Bmob-addUserSuccess", s);
                } else {
                    Log.e("Bmob-addUserFailed", "Failed");
                }
            }
        });
    }

    /**
     * 注册新用户验证
     * @param userPhone 账号
     */
    public void registerChecked(final String userPhone) {
        BmobQuery<Account> query = new BmobQuery<>();
        query.findObjects(new FindListener<Account>() {
            @Override
            public void done(List<Account> list, BmobException e) {
                for (Account a :
                        list) {
                    if (a.getAccount().equals(userPhone)) {
                        registerSuccess = false;
                        break;
                    }
                }
                if (!registerSuccess) {
                    ((RegisterActivity) context).handler.sendEmptyMessage(404);
                } else {
                    ((RegisterActivity) context).handler.sendEmptyMessage(200);
                }
            }
        });
    }

    /**
     * 登录查询是否账号密码输入错误
     * @param userPhone 账号
     * @param password  密码
     */
    public void queryAccount(final String userPhone, final String password) {
        BmobQuery<Account> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Account>() {
            @Override
            public void done(List<Account> list, BmobException e) {
                for (Account a :
                        list) {
                    if (a.getAccount().equals(userPhone) && a.getPassword().equals(password)) {
                        identifySuccess = true;
                        break;
                    }
                }
                ((LoginActivity) context).handler.sendEmptyMessage(0);
            }
        });
    }

    /**
     * 初始化动作细节
     */
    public void initSportDetailData() {
        BmobQuery<SportDetail> query = new BmobQuery<SportDetail>();
        query.findObjects(new FindListener<SportDetail>() {
            @Override
            public void done(List<SportDetail> list, BmobException e) {
                list_sportDetail.clear();
                list_sportDetail.addAll(list);
                Message message = new Message();
                message.arg2 = SPORT_DETAIL_FINISH;
                ((ChooseActionActivity) context).handler.sendMessage(message);
            }
        });


    }

    /**
     * 初始化动作名字
     */
    public void initSportNameData() {
        BmobQuery<SportName> query = new BmobQuery<SportName>();
        query.findObjects(new FindListener<SportName>() {
            @Override
            public void done(List<SportName> list, BmobException e) {
                list_sportName.clear();
                list_sportName.addAll(list);
                Message message = new Message();
                message.arg1 = SPORT_NAME_FINISH;
                ((ChooseActionActivity) context).handler.sendMessage(message);

            }
        });
    }
    /*public void initBmob() {
        //第一：默认初始化
        //Bmob.initialize(context, "f79d34f38040f7e7512a4228ea4d0c7a");
        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this);
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);
    }*/
}
