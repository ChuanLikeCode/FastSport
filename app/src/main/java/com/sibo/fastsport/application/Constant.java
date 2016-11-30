package com.sibo.fastsport.application;

/**
 * Created by Administrator on 2016/11/20.
 */
public class Constant {
    public static final String AppID = "wx970434db9a75a8b4";
    public static final String AppSecret = "4dccdd3999204da7cc4df5a06906d8a0";
    public static final String getAccessToken = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + AppID + "&secret=" + AppSecret;
    public static final String getMaterial = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=";
    public static String PATH = "config";
    public static String USERACCOUNTCOOKIE = "account";
    public static String USERPASSWORDCOOKIE = "password";
    public static String ISFIRSTSTART = "isFirst";
    public static String ISLOGIN = "isLogin";
    public static String ACCESSTOKEN = "access_token";
    public final static int SUCCESS = 0;
    public final static int NO_MORE = 1;

}
