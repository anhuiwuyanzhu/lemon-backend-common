package com.lemon.violet.utils;

public class RandomUtil {
    public static String getRandomNumber16(){
        long millis = System.currentTimeMillis();
        return String.valueOf(millis);
    }
}
