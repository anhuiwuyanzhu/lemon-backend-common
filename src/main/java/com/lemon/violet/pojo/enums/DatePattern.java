package com.lemon.violet.pojo.enums;

import lombok.Getter;

@Getter
public enum DatePattern {
    DATE_PATTERN_P1("yyyy/MM/dd"),
    DATE_PATTERN_P2("yyyy-MM-dd"),
    DATE_PATTERN_P3("yyyy/MM/dd HH/mm/ss"),
    DATE_PATTERN_P4("yyyy-MM-dd HH:mm:ss"),
    DATE_PATTERN_P5("yyyy/MM"),
    DATE_PATTERN_P6("yyyy/MM/dd/"),
    DATE_PATTERN_P7("yyyy/MM/");


    private String pattern;

    DatePattern(String pattern){
        this.pattern = pattern;
    }


}
