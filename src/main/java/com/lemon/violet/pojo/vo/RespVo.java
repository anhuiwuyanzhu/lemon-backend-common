package com.lemon.violet.pojo.vo;

import com.lemon.violet.annotation.Log;
import com.lemon.violet.annotation.LogField;
import lombok.Data;

@Log("业务出参")
@Data
public class RespVo {
    @LogField("更新结果")
    private String ret;
}
