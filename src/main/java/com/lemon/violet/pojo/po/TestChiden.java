package com.lemon.violet.pojo.po;

import com.lemon.violet.annotation.Log;
import com.lemon.violet.annotation.LogField;
import lombok.Data;

@Log("子类请求")
@Data
public class TestChiden {

    @LogField("年龄")
    private int age = 20;

    @LogField("描述")
    private String msg = "帅哥";
}
