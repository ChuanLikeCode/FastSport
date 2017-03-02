package com.sibo.fastsport.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sibo.fastsport.application.Constant;
import com.sibo.fastsport.fragment.MyPlanFragment;
import com.sibo.fastsport.utils.MyBombUtils;

/**
 * Created by zhouchuan on 2017/3/2.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("finish")){
            MyBombUtils.COUNT++;
            if (MyBombUtils.COUNT == 6){
                MyPlanFragment.handler.sendEmptyMessage(Constant.SUCCESS);
            }
        }
    }
}
