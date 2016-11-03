package com.sibo.fastsport.utils;

import android.content.Context;
import android.util.Log;

import com.sibo.fastsport.domain.SportName;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/10/31.
 */
public class BombUtils {
    public static List<SportName> list_sortName = new ArrayList<>();
    private Context context;

    public BombUtils(Context context) {
        this.context = context;
        Bmob.initialize(context, "f79d34f38040f7e7512a4228ea4d0c7a");
    }

    public void initData() {
        BmobQuery<SportName> query = new BmobQuery<SportName>();
        query.findObjects(new FindListener<SportName>() {
            @Override
            public void done(List<SportName> list, BmobException e) {
                list_sortName.addAll(list);
                for (SportName s :
                        list) {
                    Log.e("level", s.getLevel() + "");
                }

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
