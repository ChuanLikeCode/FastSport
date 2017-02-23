package com.sibo.fastsport.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by chuan on 2017/2/23.
 */

public class UserSportPlan extends BmobObject {
    private String planName; //健身计划名字
    private String account;  //标识健身教练
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }
}
