package com.sibo.fastsport.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/11/20.
 */
public class UserInfo extends BmobObject{
    private String planObjectId;
    private String type;//1---教练  2----学员
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlanObjectId() {
        return planObjectId;
    }

    public void setPlanObjectId(String planObjectId) {
        this.planObjectId = planObjectId;
    }

}
