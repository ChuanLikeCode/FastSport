package com.sibo.fastsport.utils;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import com.sibo.fastsport.application.Constant;
import com.sibo.fastsport.application.MyApplication;
import com.sibo.fastsport.domain.SportName;
import com.sibo.fastsport.model.DayPlan;
import com.sibo.fastsport.model.MainAction;
import com.sibo.fastsport.model.RelaxAction;
import com.sibo.fastsport.model.Stretching;
import com.sibo.fastsport.model.UserSportPlan;
import com.sibo.fastsport.model.WarmUp;
import com.sibo.fastsport.ui.MakePlanActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 收集健身计划
 * Created by chuan on 2017/2/23.
 */

public class CollectPlan {

    private Context context;
    //public static List<UserSportPlan> userSportPlan = new ArrayList<>();//教练的健身计划 名字 教练
    public static List<Integer> dayId = new ArrayList<>();//选择天数的集合
    public static String id;
    public static UserSportPlan userSportPlan = new UserSportPlan();//教练的健身计划 名字 教练
    public static List<DayPlan> dayPlan = new ArrayList<>();//计划的具体内容 第几天的计划 动作类型的有无
    public static List<WarmUp> warmUps = new ArrayList<>();//热身动作
    public static List<Stretching> stretchings = new ArrayList<>();//拉伸动作
    public static List<MainAction> mainActions = new ArrayList<>();//具体动作
    public static List<RelaxAction> relaxActions = new ArrayList<>();//放松动作
    //动作类别 天数 名字 这样的存储方案
    // 热身--1、拉伸--2、具体--3、放松--4
    public static SparseArray<SparseArray<List<SportName>>> typeDayNamePlan = new SparseArray<>();

    public CollectPlan(Context context){
        this.context = context;
    }
    /**
     * 收集健身计划
     */
    public static void initDayPlan(){
        for (int i = 0;i<7;i++){
            DayPlan d = new DayPlan();
            d.setDayId(i);
            dayPlan.add(d);
        }
    }

    public static void prepareToPush(){

        for (int i = 0;i < 7 ; i++){
            if (MakePlanUtils.list_day.get(i).warmUpList.size() != 0){
                for (SportName s :
                        MakePlanUtils.list_day.get(i).warmUpList) {
                    WarmUp w = new WarmUp();
                    w.setDayId(i);
                    w.setId(id);
                    w.setWarmId(s.getObjectId());
                    warmUps.add(w);
                }
            }

        }
        for (int i = 0;i < 7 ; i++){
            if (MakePlanUtils.list_day.get(i).stretchingList.size() != 0){
                for (SportName s :
                        MakePlanUtils.list_day.get(i).stretchingList) {
                    Stretching stre = new Stretching();
                    stre.setDayId(i);
                    stre.setId(id);
                    stre.setStretchingId(s.getObjectId());
                    stretchings.add(stre);
                }
            }

        }
        for (int i = 0;i < 7 ; i++){
            if (MakePlanUtils.list_day.get(i).mainActionList.size() != 0){
                for (SportName s :
                        MakePlanUtils.list_day.get(i).mainActionList) {
                    MainAction m = new MainAction();
                    m.setDayId(i);
                    m.setId(id);
                    m.setMainAction(s.getObjectId());
                    mainActions.add(m);
                }
            }

        }
        for (int i = 0;i < 7 ; i++){
            if (MakePlanUtils.list_day.get(i).relaxActionList.size() != 0){
                for (SportName s :
                        MakePlanUtils.list_day.get(i).relaxActionList) {
                    RelaxAction r = new RelaxAction();
                    r.setDayId(i);
                    r.setId(id);
                    r.setRelaxAction(s.getObjectId());
                    relaxActions.add(r);
                }
            }

        }
        //Log.e("ADD_WARM",MyBombUtils.ADD_MAIN +""+MyBombUtils.ADD_STRE);
//        for (int i = 1;i < 8 ; i++){
//            List<SportName> sportNamesWarm = MakePlanUtils.sp_warmUp.get(i,null);
//            //Log.e("sportNamesWarm",sportNamesWarm.size()+"");
//            List<SportName> sportNamesStretching = MakePlanUtils.sp_stretching.get(i,null);
//            List<SportName> sportNamesMainAction = MakePlanUtils.sp_mainAction.get(i,null);
//            List<SportName> sportNamesRelaxAction = MakePlanUtils.sp_relaxAction.get(i,null);
//            //热身动作计划的集合
//            if (sportNamesWarm != null){
//                for (SportName s :
//                        sportNamesWarm) {
//                    WarmUp warmUp = new WarmUp();
//                    warmUp.setId(id);
//                    warmUp.setDayId(i);
//                    warmUp.setWarmId(s.getObjectId());
//                    warmUps.add(warmUp);
//                    Log.e("day",sportNamesWarm.size()+"--"+i+"--"+warmUps.size());
//                }
//            }
//            //拉伸动作计划的集合
//            if (sportNamesStretching != null){
//                for (SportName s :
//                        sportNamesStretching) {
//                    Stretching stretching = new Stretching();
//                    stretching.setDayId(i);
//                    stretching.setId(id);
//                    stretching.setStretchingId(s.getObjectId());
//                    stretchings.add(stretching);
//                }
//            }
//            //具体动作计划的集合
//            if (sportNamesMainAction != null){
//                for (SportName s :
//                        sportNamesMainAction) {
//                    MainAction mainAction  = new MainAction();
//                    mainAction.setDayId(i);
//                    mainAction.setId(id);
//                    mainAction.setMainAction(s.getObjectId());
//                    mainActions.add(mainAction);
//                }
//            }
//            //放松动作计划的集合
//            if (sportNamesRelaxAction != null){
//                for (SportName s :
//                        sportNamesRelaxAction) {
//                    RelaxAction relaxAction = new RelaxAction();
//                    relaxAction.setDayId(i);
//                    relaxAction.setId(id);
//                    relaxAction.setRelaxAction(s.getObjectId());
//                    relaxActions.add(relaxAction);
//                }
//            }
//        }

    }

    /**
     * 增加健身计划名字
     * @param userSportPlan
     */
//    public void addPlan(UserSportPlan userSportPlan){
//
//        userSportPlan.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if (e == null){
//                    id = s;
//                    prepareToPush();
//                    addDayPlan(dayPlan.get(0),0);
//                    Log.e("addPlan",s);
//
//                }else {
//                    Log.e("addPlan","failed");
//                }
//            }
//        });
//    }
//    /**
//     * 上传健身计划 利用递归
//     * @param d
//     * @param i
//     */
//    public void addDayPlan(DayPlan d , int i ){
//        i++;
//        d.setId(id);
//        final int finalI = i;
//        d.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if (e == null){
//                    if (finalI != 7){
//                        addDayPlan(dayPlan.get(finalI), finalI);
//                    }else {
//                        if (!warmUps.isEmpty())
//                            addWarmUp(warmUps.get(0),0);
//                    }
//                    Log.e("addDayPlan",s);
//
//                }else {
//                    Log.e("addDayPlan","failed");
//                }
//            }
//        });
//    }
//
//    public void addWarmUp(WarmUp w , int i){
//        i++;
//        final int finalI = i;
//        w.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if (e == null){
//                    if (finalI != warmUps.size() ){
//                        addWarmUp(warmUps.get(finalI),finalI);
//                    }else {
//                        addStretching(stretchings.get(0),0);
//                    }
//                    Log.e("addWarmUp",s);
//
//                }else {
//                    Log.e("addWarmUp","failed");
//                }
//            }
//        });
//    }
//
//
//    public void addStretching(Stretching s , int i){
//       i++;
//        final int finalI = i;
//        s.save(new SaveListener<String>() {
//                @Override
//                public void done(String s, BmobException e) {
//                    if (e == null){
//                        if (finalI != stretchings.size()){
//                            addStretching(stretchings.get(finalI),finalI);
//                        }else {
//                            addMainAction(mainActions.get(0),0);
//                        }
//                        Log.e("addStretching",s);
//
//                    }else {
//                        Log.e("addStretching","failed");
//                    }
//                }
//            });
//
//    }
//    public void addMainAction(MainAction m , int i){
//        i++;
//
//        final int finalI = i;
//        m.save(new SaveListener<String>() {
//                @Override
//                public void done(String s, BmobException e) {
//                    if (e == null){
//                        if (finalI != mainActions.size()){
//                            addMainAction(mainActions.get(finalI),finalI);
//                        }else {
//                            addRelaxAction(relaxActions.get(0),0);
//                        }
//                        Log.e("addMainAction",s);
//
//                    }else {
//                        Log.e("addMainAction","failed");
//                    }
//                }
//            });
//
//    }
//    public void addRelaxAction(RelaxAction r , int i){
//        i++;
//
//        final int finalI = i;
//        r.save(new SaveListener<String>() {
//                @Override
//                public void done(String s, BmobException e) {
//                    if (e == null){
//                        //((MakePlanActivity)context).handler.sendEmptyMessage(Constant.SUCCESS);
//                        if (finalI != relaxActions.size()){
//                            addRelaxAction(relaxActions.get(finalI),finalI);
//                        }else {
//                            ((MakePlanActivity)context).handler.sendEmptyMessage(Constant.SUCCESS);
//                        }
//                        Log.e("addRelaxAction",s);
//
//                    }else {
//                        Log.e("addRelaxAction","failed");
//                    }
//                }
//            });
//        }


}