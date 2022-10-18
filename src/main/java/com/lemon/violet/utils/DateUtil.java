package com.lemon.violet.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    //获取下周x的日期,dayOfWeek表示下周几，具体的数值可以充Calendar的常量类里获取 比如 周一:Calendar.MONDAY
    public static String getNextTuesday(int dayOfWeek) {

        //设置一周从周一开始 北美默认一周从周日开始
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);

        //获取下周x日期
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);


        //日期格式处理 并返回时间: yyyy-MM-dd
        Date time = cal.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(time);
    }
}
