package com.sibo.fastsport.utils;

import android.util.SparseArray;

import com.sibo.fastsport.application.MyApplication;
import com.sibo.fastsport.domain.SportName;
import com.sibo.fastsport.model.DayPlan;
import com.sibo.fastsport.model.MainAction;
import com.sibo.fastsport.model.RelaxAction;
import com.sibo.fastsport.model.Stretching;
import com.sibo.fastsport.model.UserSportPlan;
import com.sibo.fastsport.model.WarmUp;

import java.util.ArrayList;
import java.util.List;

/**
 * 收集健身计划
 * Created by chuan on 2017/2/23.
 */

public class CollectPlan {


    //public static List<UserSportPlan> userSportPlan = new ArrayList<>();//教练的健身计划 名字 教练
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

    /**
     * 收集健身计划
     */
    public static void getPlanInfo(){

    }

    public static void prepareToPush(){


    }
}