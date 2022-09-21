package com.lemon.violet.pojo.rto;


import com.lemon.violet.annotation.Log;
import com.lemon.violet.annotation.LogField;
import com.lemon.violet.annotation.LogRef;
import lombok.Data;

import java.util.List;

@Log("父类请求")
@Data
public class TestParent {
    @LogField("欢迎")
    private String hello;

    @LogField("数组")
    private List<Integer> vals;

    @LogRef
    private List<TestChiden> p;
}
