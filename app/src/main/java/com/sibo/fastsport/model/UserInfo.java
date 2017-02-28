package com.sibo.fastsport.model;

/**
 * Created by Administrator on 2016/11/20.
 */
public class UserInfo {
    private boolean isLogin;
    private String planObjectId;
    private String type;//1---教练  2----学员

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

    public boolean getIsLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
