package com.lemon.violet.pojo.vo;

import lombok.Data;

@Data
public class PageVo<T> {
    private T data;

    private Long count;
}
