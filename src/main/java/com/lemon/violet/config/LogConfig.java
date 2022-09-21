package com.lemon.violet.config;

import com.lemon.violet.aop.LogHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class LogConfig {

    @Bean
    @ConditionalOnMissingBean
    public LogHandler logHandler(){
        return new LogHandler();
    }
}
