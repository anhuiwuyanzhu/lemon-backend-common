package com.lemon.violet.pojo.vo;

import com.lemon.violet.annotation.Log;
import com.lemon.violet.annotation.LogField;
import com.lemon.violet.annotation.LogRef;
import com.lemon.violet.pojo.enums.CodeEnum;
import lombok.Data;

@Log("出参")
@Data
public class RespVo <T> {

    @LogField("响应码")
    private String code;

    @LogRef("响应体")
    private T ret;

    @LogField("响应信息")
    private String msg;


    private RespVo(String code, T obj, String msg) {
        this.code = code;
        this.ret = obj;
        this.msg = msg;
    }


    //成功响应
    public static <T> RespVo  success(T obj){
        return new RespVo<>(CodeEnum.RESULT_200.getCode(),obj,CodeEnum.RESULT_200.getMsg());
    }

    //失败响应
    public static  RespVo fail(String msg){
        return new RespVo(CodeEnum.RESULT_400.getCode(), null, msg);
    }
}
