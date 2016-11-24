package com.sibo.fastsport.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/11/20.
 */
public class Account extends BmobObject {
    private String account;
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
