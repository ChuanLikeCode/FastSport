package com.sibo.fastsport.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.sibo.fastsport.application.Constant;
import com.sibo.fastsport.application.MyApp;
import com.sibo.fastsport.domain.MyCollections;
import com.sibo.fastsport.domain.SportDetail;
import com.sibo.fastsport.domain.SportName;
import com.sibo.fastsport.fragment.MyPlanFragment;
import com.sibo.fastsport.model.Account;
import com.sibo.fastsport.model.DayPlan;
import com.sibo.fastsport.model.MainAction;
import com.sibo.fastsport.model.RelaxAction;
import com.sibo.fastsport.model.Stretching;
import com.sibo.fastsport.model.UserInfo;
import com.sibo.fastsport.model.UserSportPlan;
import com.sibo.fastsport.model.WarmUp;
import com.sibo.fastsport.receiver.MyBroadcastReceiver;
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
import cn.bmob.v3.listener.UpdateListener;


/**
 * Bomb服务器的工具类
 * Created by Administrator on 2016/10/31.
 */
public class MyBombUtils {


    private final static int SPORT_NAME_FINISH = 1;
    private final static int SPORT_DETAIL_FINISH = 2;
    public static int COUNT = 0;
    public static int MAKE = 0;
    public static int addDayPlan = 0;
    public static List<SportName> list_sportName = new ArrayList<SportName>();
    public static List<SportDetail> list_sportDetail = new ArrayList<SportDetail>();
    public static boolean isFirst = true;

    public static boolean identifySuccess = false;
    /**
     * 获取用户的健身名字和id
     */
    public static UserSportPlan userSportPlan;
    /**
     * 获取用户的每天健身计划
     */
    public static List<DayPlan> list_userDayPlan = new ArrayList<>();
    /**
     * 获取热身动作
     */
    public static List<WarmUp> list_warmUp = new ArrayList<>();
    public static List<SportName> list_warmPlan = new ArrayList<>();
    /**
     * 获取拉伸动作
     */
    public static List<Stretching> list_stretching = new ArrayList<>();
    public static List<SportName> list_stretchPlan = new ArrayList<>();
    /**
     * 获取具体动作
     */
    public static List<MainAction> list_mainAction = new ArrayList<>();
    public static List<SportName> list_mainActionPlan = new ArrayList<>();
    /**
     * 获取放松动作
     */
    public static List<RelaxAction> list_relaxAction = new ArrayList<>();
    public static List<SportName> list_relaxActionPlan = new ArrayList<>();

    public Context context;
    private boolean registerSuccess = true;
    private MyBroadcastReceiver receiver;
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
                    if (m.getAccount().equals(MyApp.mAccount.getAccount())){
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
                    //((MakePlanActivity)context).handler2.sendEmptyMessage(Constant.SHOW);
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
            d.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null){
                        Log.e("addDayPlan",s);
                        ((MakePlanActivity) context).handler2.sendEmptyMessage(Constant.SHOW);
                    }else {
                        Log.e("addDayPlan","failed");
                    }
                }
            });
        }
    }

    /**************************************************************************************************/

    public void addWarmUp() {
        Log.e("Bmobsp_warmUp", CollectPlan.warmUps.size()+"");
        if (CollectPlan.warmUps.size()!=0){
            for (WarmUp w : CollectPlan.warmUps){

                w.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null){
                            Log.e("addWarmUp", s);
                            Intent intent = new Intent("makePlan");
                            intent.putExtra("up", 1);
                            context.sendBroadcast(intent);
                        }else {
                            Log.e("addWarmUp","failed");
                        }
                    }
                });
            }
        }

    }

    public void addStretching(){
        //Log.e("addStretching",CollectPlan.stretchings.size()+"");
        if (CollectPlan.stretchings.size() != 0){
            for (Stretching s : CollectPlan.stretchings){
                //stre++;
                s.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null){
                            Log.e("addStretching", s);
                            Intent intent = new Intent("makePlan");
                            intent.putExtra("up", 1);
                            context.sendBroadcast(intent);
                        }else {
                            Log.e("addStretching","failed");
                        }
                    }
                });
            }
        }

    }

    public void addMainAction(){
        //Log.e("addMainAction",CollectPlan.mainActions.size()+"");
        if (CollectPlan.mainActions.size() != 0){
            for (MainAction m : CollectPlan.mainActions){
                //mainAction++;
                m.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null){
                            Log.e("addMainAction", s);
                            Intent intent = new Intent("makePlan");
                            intent.putExtra("up", 1);
                            context.sendBroadcast(intent);
                        }else {
                            Log.e("addMainAction","failed");
                        }
                    }
                });
            }
        }

    }

    public void addRelaxAction(){
      //  Log.e("addRelaxAction",CollectPlan.relaxActions.size()+"");
        if (CollectPlan.relaxActions.size() != 0){
            for (RelaxAction r : CollectPlan.relaxActions){
                //relaxAction++;
                r.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null){

                            Log.e("addRelaxAction", s);
                            Intent intent = new Intent("makePlan");
                            intent.putExtra("up", 1);
                            context.sendBroadcast(intent);
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
     *
     */
    public void addUser(Account account) {
        account.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Log.e("Bmob-addUserSuccess", s);
                    addUserInfo(MyApp.mUser);
                } else {
                    Log.e("Bmob-addUserFailed", "Failed");
                }
            }
        });
    }

    /**
     * 增加用户信息
     * @param userInfo
     */
    public void addUserInfo(UserInfo userInfo){
        userInfo.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {

                    Log.e("Bmob-addUserInfoSuccess", s);
                } else {
                    Log.e("Bmob-addUserInfoFailed", "Failed");
                }
            }
        });
    }
/**************************************************************************************/
    public void getUserSportPlan(final String id){
        BmobQuery<UserSportPlan> query = new BmobQuery<>();
        query.findObjects(new FindListener<UserSportPlan>() {
            @Override
            public void done(List<UserSportPlan> list, BmobException e) {
                if (e == null) {
                    Log.e("Bmob-UserSportSuccess", "ok");
                    Intent intent = new Intent("scannerFinish");
                    intent.putExtra("finish",1);
                    context.sendBroadcast(intent);
                    for (UserSportPlan s : list){
                        if (id.equals(s.getObjectId())){
                            userSportPlan = s;
                            break;
                        }
                    }
                } else {
                    MyPlanFragment.handler.sendEmptyMessage(Constant.FAILED);
                    Log.e("Bmob-UserSport", "Failed");
                }
            }
        });
    }

    public void getDayPlan(final String id){
        BmobQuery<DayPlan> query = new BmobQuery<>();
        query.findObjects(new FindListener<DayPlan>() {
            @Override
            public void done(List<DayPlan> list, BmobException e) {
                if (e == null) {
                    Log.e("Bmob-getDayPlanSuccess", "ok");
                    Intent intent = new Intent("scannerFinish");
                    intent.putExtra("finish",1);
                    context.sendBroadcast(intent);
                    for (DayPlan d : list){
                        if (d.getId().equals(id)){
                            list_userDayPlan.add(d);
                        }
                    }
                } else {
                    Log.e("Bmob-getDayPlanFailed", "Failed");
                }

            }
        });
    }
    /**
     * 获取所有的健身动作名字
     */
    public void getPlanAllName(){
        BmobQuery<SportName> query = new BmobQuery<>();
        query.findObjects(new FindListener<SportName>() {
            @Override
            public void done(List<SportName> list, BmobException e) {
                    if (e == null){
                        list_sportName.clear();
                        list_sportName.addAll(list);
                        Intent intent = new Intent("scannerFinish");
                        intent.putExtra("finish",1);
                        context.sendBroadcast(intent);
                    }else {
                        Log.e("getPlanAllName","failed");
                    }
            }
        });
    }

    /**
     * 获取所有的健身动作细节
     */
    public void getPlanAllDetail(){
        BmobQuery<SportDetail> query = new BmobQuery<>();
        query.findObjects(new FindListener<SportDetail>() {
            @Override
            public void done(List<SportDetail> list, BmobException e) {
                if (e == null){
                    list_sportDetail.clear();
                    list_sportDetail.addAll(list);
                    Intent intent = new Intent("scannerFinish");
                    intent.putExtra("finish",1);
                    context.sendBroadcast(intent);
                }else {
                    Log.e("getPlanAllName","failed");
                }
            }
        });
    }
    public void getWarmUp(final String id){
        BmobQuery<WarmUp> query = new BmobQuery<>();
        query.findObjects(new FindListener<WarmUp>() {
            @Override
            public void done(List<WarmUp> list, BmobException e) {
                if (e == null) {
                    Log.e("Bmob-getWarmUpSuccess", "ok");
                    Intent intent = new Intent("scannerFinish");
                    intent.putExtra("finish",1);
                    context.sendBroadcast(intent);
                    for (WarmUp w : list){
                        if (w.getId().equals(id)){
                            list_warmUp.add(w);
                        }
                    }
                } else {
                    Log.e("Bmob-getWarmUpFailed", "Failed");
                }
            }
        });
    }

    public void getStretching(final String id){
        BmobQuery<Stretching> query = new BmobQuery<>();
        query.findObjects(new FindListener<Stretching>() {
            @Override
            public void done(List<Stretching> list, BmobException e) {
                if (e == null) {
                    Log.e("Bmob-getWarmUpSuccess", "ok");
                    Intent intent = new Intent("scannerFinish");
                    intent.putExtra("finish",1);
                    context.sendBroadcast(intent);
                    for (Stretching s : list){
                        if (s.getId().equals(id)){
                            list_stretching.add(s);
                        }
                    }
                } else {
                    Log.e("Bmob-getWarmUpFailed", "Failed");
                }
            }
        });
    }

    public void getMainAction(final String id){
        BmobQuery<MainAction> query = new BmobQuery<>();
        query.findObjects(new FindListener<MainAction>() {
            @Override
            public void done(List<MainAction> list, BmobException e) {
                if (e == null) {
                    Log.e("Bmob-getWarmUpSuccess", "ok");
                    Intent intent = new Intent("scannerFinish");
                    intent.putExtra("finish",1);
                    context.sendBroadcast(intent);
                    for (MainAction m : list){
                        if (m.getId().equals(id)){
                            list_mainAction.add(m);
                        }
                    }
                } else {
                    Log.e("Bmob-getWarmUpFailed", "Failed");
                }
            }
        });
    }

    public void getRelaxAction(final String id){
        BmobQuery<RelaxAction> query = new BmobQuery<>();
        query.findObjects(new FindListener<RelaxAction>() {
            @Override
            public void done(List<RelaxAction> list, BmobException e) {
                if (e == null) {
                    Log.e("Bmob-getWarmUpSuccess", "ok");
                    Intent intent = new Intent("scannerFinish");
                    intent.putExtra("finish",1);
                    context.sendBroadcast(intent);
                    for (RelaxAction r : list){
                        if (r.getId().equals(id)){
                            list_relaxAction.add(r);
                        }
                    }
                } else {
                    Log.e("Bmob-getWarmUpFailed", "Failed");
                }
            }
        });
    }
 /**************************************************************************************************/

    /**
     * 更新User信息
     * @param userInfo
     */
    public void updateUserInfo(UserInfo userInfo){
        userInfo.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.e("updateUserInfo", "ok");
                } else {
                    e.printStackTrace();
                    Log.e("updateUserInfo", "failed");
                }
            }
        });
    }

    /**
     * 注册新用户验证
     * @param userPhone 账号
     */
    public void registerChecked(final String userPhone) {
        BmobQuery<UserInfo> query = new BmobQuery<>();
        query.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> list, BmobException e) {
                for (UserInfo a :
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
                if (e == null) {
                    Log.e("Bmob-querySuccess", "ok");
                    for (Account a :
                            list) {
                        if (a.getAccount().equals(userPhone) && a.getPassword().equals(password)) {
                            identifySuccess = true;
                            MyApp.mUser.setAccount(userPhone);
                            break;
                        }
                    }
                    BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
                    query.findObjects(new FindListener<UserInfo>() {
                        @Override
                        public void done(List<UserInfo> list, BmobException e) {
                            ((LoginActivity) context).handler.sendEmptyMessage(0);
                            for (UserInfo a :
                                    list) {
                                //Log.e("account",a.getAccount()+"---"+MyApp.mUser.getAccount());
                                if (MyApp.mUser.getAccount().equals(a.getAccount())) {
                                    MyApp.mUser.setType(a.getType());
                                    MyApp.mUser.setPlanObjectId(a.getPlanObjectId());
                                    break;
                                }
                            }
                        }
                    });
                } else {
                    e.printStackTrace();
                    Log.e("Bmob-updateInfoFailed", "Failed");
                }

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
                if (e == null){
                    list_sportDetail.clear();
                    list_sportDetail.addAll(list);
                    Message message = new Message();
                    message.arg2 = SPORT_DETAIL_FINISH;
                    ((ChooseActionActivity) context).handler.sendMessage(message);
                }else {
                    Toast.makeText(context,"获取失败，请返回重新获取",Toast.LENGTH_SHORT).show();
                }

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
                if (e == null){
                    list_sportName.clear();
                    list_sportName.addAll(list);
                    Message message = new Message();
                    message.arg1 = SPORT_NAME_FINISH;
                    ((ChooseActionActivity) context).handler.sendMessage(message);
                }else {
                    Toast.makeText(context,"获取失败，请返回重新获取",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
