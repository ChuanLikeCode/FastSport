package com.sibo.fastsport.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yyuand on 2016.11.29.
 */

public class DateTransformUtils {
    // 长日期格式
    public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String transfromDate(String date){
        long tim = Long.parseLong(date);
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
        Date date1 = new Date(tim);
        return sdf.format(date1);
    }
}
