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
 * Created by Administrator on 2016/10/31.
 */
public class MyBombUtils {

    private final static int SPORT_NAME_FINISH = 1;
    private final static int SPORT_DETAIL_FINISH = 2;

    public static List<SportName> list_sportName = new ArrayList<SportName>();
    public static List<SportDetail> list_sportDetail = new ArrayList<SportDetail>();
    //动作难度归类  1----5
    public static List<SportName> list_level_1 = new ArrayList<SportName>();
    public static List<SportName> list_level_2 = new ArrayList<SportName>();
    public static List<SportName> list_level_3 = new ArrayList<SportName>();
    public static List<SportName> list_level_4 = new ArrayList<SportName>();
    public static List<SportName> list_level_5 = new ArrayList<SportName>();
    //动作类型归类  热身1 拉伸2 具体3 放松4
    public static List<SportName> list_type_reshen = new ArrayList<SportName>();
    public static List<SportName> list_type_lashen = new ArrayList<SportName>();
    public static List<SportName> list_type_juti = new ArrayList<SportName>();
    public static List<SportName> list_type_fangsong = new ArrayList<>();
    //动作部位归类  胸部0、肩部1、背部2、肱二头肌3、肱三头肌4、腿部5、前臂6、腹部7
    public static List<SportName> list_part_chest = new ArrayList<SportName>();
    public static List<SportName> list_part_jianbu = new ArrayList<SportName>();
    public static List<SportName> list_part_beibu = new ArrayList<SportName>();
    public static List<SportName> list_part_gongerji = new ArrayList<SportName>();
    public static List<SportName> list_part_gongsanji = new ArrayList<SportName>();
    public static List<SportName> list_part_tuibu = new ArrayList<SportName>();
    public static List<SportName> list_part_qianbi = new ArrayList<SportName>();
    public static List<SportName> list_part_fubu = new ArrayList<SportName>();
    //所属器材归类 哑铃、健身房器械、无器械、杠铃
    public static List<SportName> list_equipment_yaling = new ArrayList<SportName>();
    public static List<SportName> list_equipment_jianshenfang = new ArrayList<SportName>();
    public static List<SportName> list_equipment_wuqixie = new ArrayList<SportName>();
    public static List<SportName> list_equipment_gangling = new ArrayList<SportName>();
    public static boolean isFirst = true;
    private Context context;
    public MyBombUtils(Context context) {
        this.context = context;
        Bmob.initialize(context, "f79d34f38040f7e7512a4228ea4d0c7a");
    }

    private SportName matcher(String name) {
        for (SportName s :
                list_sportName) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        return null;
    }

    private void initEquipment() {
        for (SportDetail s :
                list_sportDetail) {
            switch (s.getNeed_equipment()) {
                case "哑铃":
                    list_equipment_yaling.add(matcher(s.getName()));
                    break;
                case "健身房器械":
                    list_equipment_jianshenfang.add(matcher(s.getName()));
                    break;
                case "无器械":
                    list_equipment_wuqixie.add(matcher(s.getName()));
                    break;
                case "杠铃":
                    list_equipment_gangling.add(matcher(s.getName()));
                    break;
            }
        }
    }

    private void initPart() {
        for (SportDetail s :
                list_sportDetail) {
            switch (s.getExercise_part()) {
                case "胸部":
                    list_part_chest.add(matcher(s.getName()));
                    break;
                case "肩部":
                    list_part_jianbu.add(matcher(s.getName()));
                    break;
                case "背部":
                    list_part_beibu.add(matcher(s.getName()));
                    break;
                case "肱二头肌":
                    list_part_gongerji.add(matcher(s.getName()));
                    break;
                case "肱三头肌":
                    list_part_gongsanji.add(matcher(s.getName()));
                    break;
                case "腿部":
                    list_part_tuibu.add(matcher(s.getName()));
                    break;
                case "前臂":
                    list_part_qianbi.add(matcher(s.getName()));
                    break;
                case "腹部":
                    list_part_fubu.add(matcher(s.getName()));
                    break;
            }
        }
    }

    private void initLevel() {
        for (SportName s :
                list_sportName) {
            switch (s.getLevel()) {
                case 1:

                    list_level_1.add(s);
                    break;
                case 2:
                    list_level_2.add(s);
                    break;
                case 3:
                    list_level_3.add(s);
                    break;
                case 4:
                    list_level_4.add(s);
                    break;
                case 5:
                    list_level_5.add(s);
                    break;
            }
        }
    }

    private void initType() {
        for (SportName s :
                list_sportName) {
            switch (s.getType()) {
                case "热身动作":
                    list_type_reshen.add(s);
                    break;
                case "拉伸":
                    list_type_lashen.add(s);
                    break;
                case "具体动作":
                    list_type_juti.add(s);
                    break;
                case "放松动作":
                    list_type_fangsong.add(s);
                    break;
            }
        }
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
                initEquipment();
                initPart();
                Message message = new Message();
                message.arg1 = SPORT_DETAIL_FINISH;
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
                initLevel();
                initType();
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
