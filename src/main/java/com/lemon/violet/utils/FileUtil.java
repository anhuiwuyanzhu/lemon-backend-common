package com.lemon.violet.utils;

import com.lemon.violet.pojo.enums.DatePattern;
import org.springframework.util.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FileUtil {

    /**
     * 获取文件后缀名 例：sg.png 得到 .png
     * @param name 文件名
     * @return
     */
    public static String getSuffix(String name) {
        String subStr = null;
        if(StringUtils.hasText(name)){
            int index = name.lastIndexOf(".");
            subStr = name.substring(index);
        }
        return subStr;
    }

    /**
     * 生成文件名 生成：日期+随机+文件后缀
     * @param originName 原始名称
     * @param datePattern 日期类型
     * @return
     */
    public static String generateFileName(String originName, DatePattern datePattern){
        if(!StringUtils.hasText(originName)) {
            return null;
        }
        Date time = Calendar.getInstance().getTime();
        String dateStr = new SimpleDateFormat(datePattern.getPattern()).format(time);
        String fileName = generateFileName(originName);

        return dateStr+fileName;
    }

    /**
     * 生成文件名 生成：随机+文件后缀
     * @param originName
     * @return
     */
    public static String generateFileName(String originName){
        if(!StringUtils.hasText(originName)) {
            return null;
        }
        String suffix = getSuffix(originName);
        String fileObName = RandomUtil.getRandomNumber16();
        return fileObName+suffix;
    }
}
