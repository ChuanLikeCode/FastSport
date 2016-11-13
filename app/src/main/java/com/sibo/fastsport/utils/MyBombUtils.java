package com.sibo.fastsport.utils;

import android.content.Context;
import android.os.Message;

import com.sibo.fastsport.domain.SportDetail;
import com.sibo.fastsport.domain.SportName;
import com.sibo.fastsport.ui.ChooseActionActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * Bomb服务器的工具类
 * Created by Administrator on 2016/10/31.
 */
public class MyBombUtils {

    private final static int SPORT_NAME_FINISH = 1;
    private final static int SPORT_DETAIL_FINISH = 2;

    public static List<SportName> list_sportName = new ArrayList<SportName>();
    public static List<SportDetail> list_sportDetail = new ArrayList<SportDetail>();
    public static boolean isFirst = true;
    private Context context;
    public MyBombUtils(Context context) {
        this.context = context;
        Bmob.initialize(context, "f79d34f38040f7e7512a4228ea4d0c7a");
    }


    /**
     * 初始化动作细节
     */
    public void initSportDetailData() {
        BmobQuery<SportDetail> query = new BmobQuery<SportDetail>();
        query.findObjects(new FindListener<SportDetail>() {
            @Override
            public void done(List<SportDetail> list, BmobException e) {
                list_sportDetail.clear();
                list_sportDetail.addAll(list);
                Message message = new Message();
                message.arg2 = SPORT_DETAIL_FINISH;
                ((ChooseActionActivity) context).handler.sendMessage(message);
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
                list_sportName.clear();
                list_sportName.addAll(list);
                Message message = new Message();
                message.arg1 = SPORT_NAME_FINISH;
                ((ChooseActionActivity) context).handler.sendMessage(message);

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
