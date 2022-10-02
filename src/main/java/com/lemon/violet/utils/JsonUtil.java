package com.lemon.violet.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ObjectUtils;

public class JsonUtil<R> {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static <R,T> R convertToTarget(T r) throws JsonProcessingException {

        //非空判断
        if(ObjectUtils.isEmpty(r)) {
            return null;
        }

        //类型转换
        String jsonStr = objectMapper.writeValueAsString(r);

        R ret = objectMapper.readValue(jsonStr, new TypeReference<R>() {
        });
        Class<?> aClass = ret.getClass();

        return ret;
    }
}
