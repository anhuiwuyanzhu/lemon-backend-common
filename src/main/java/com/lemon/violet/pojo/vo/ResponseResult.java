package com.lemon.violet.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lemon.violet.annotation.Log;
import com.lemon.violet.annotation.LogField;
import com.lemon.violet.annotation.LogRef;
import com.lemon.violet.pojo.enums.CodeEnum;
import lombok.Data;

import java.io.Serializable;

@Log("出参")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T> implements Serializable {

    @LogField("响应码")
    private String code;

    @LogRef("响应体")
    private T ret;

    @LogField("响应信息")
    private String msg;


    private ResponseResult(String code, T obj, String msg) {
        this.code = code;
        this.ret = obj;
        this.msg = msg;
    }


    //成功响应
    public static <T> ResponseResult success(T obj){
        return new ResponseResult<>(CodeEnum.SUCCESS.getCode(),obj,CodeEnum.SUCCESS.getMsg());
    }

    //失败响应
    public static ResponseResult fail(CodeEnum codeEnum){
        return new ResponseResult(codeEnum.getCode(), null, codeEnum.getCode());
    }

    //自定义响应
    public static <T> ResponseResult exception(String code,T obj,String msg){
        return new ResponseResult(code,obj, msg);
    }
}
