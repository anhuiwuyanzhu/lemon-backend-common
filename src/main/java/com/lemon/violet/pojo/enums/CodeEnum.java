package com.lemon.violet.pojo.enums;

import lombok.Getter;

@Getter
public enum CodeEnum {
    RESULT_200("200","请求成功"),
    RESULT_400("400","请求失败"),
    RESULT_500("500","服务器异常");

    private String code;
    private String msg;

    CodeEnum(String code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
