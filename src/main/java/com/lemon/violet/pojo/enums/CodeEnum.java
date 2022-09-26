package com.lemon.violet.pojo.enums;

import lombok.Getter;

@Getter
public enum CodeEnum {
    //成功
    SUCCESS(200,"操作成功"),
    //登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    PHONE_NUMBER_EXIST(502,"手机号已存在"),
    REQUIRE_USERNAME(504,"必须填写用户名"),
    LOGIN_ERROR(505,"用户名或密码错误");

    private Integer code;
    private String msg;

    CodeEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
