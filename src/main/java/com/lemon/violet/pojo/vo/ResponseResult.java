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
    private Integer code;

    @LogRef("响应体")
    private T data;

    @LogField("响应信息")
    private String msg;


    private ResponseResult(Integer code, T obj, String msg) {
        this.code = code;
        this.data = obj;
        this.msg = msg;
    }


    //成功响应
    public static <T> ResponseResult success(T obj){
        return new ResponseResult<>(CodeEnum.SUCCESS.getCode(),obj,CodeEnum.SUCCESS.getMsg());
    }

    //失败响应
    public static ResponseResult fail(CodeEnum codeEnum){
        return new ResponseResult(codeEnum.getCode(), null, codeEnum.getMsg());
    }

    //自定义响应
    public static <T> ResponseResult customize(Integer code,T obj,String msg){
        return new ResponseResult(code,obj, msg);
    }


    //校验错误
    public static <T> ResponseResult checkFail(String obj){
        return new ResponseResult(202,null,obj);
    }


}
