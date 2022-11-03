package com.lemon.violet.pojo.rto;

import lombok.Data;

@Data
public class PageRto {
    private Integer pageNo = 1;

    private Integer pageSize=12;
}
