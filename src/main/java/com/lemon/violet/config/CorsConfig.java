package com.lemon.violet.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
        registry
                //跨域请求的路径
                .addMapping("/**")
                //允许跨域请求的域名
                .allowedOriginPatterns("*")
                //是否运行cookie
                .allowCredentials(true)
                //允许的请求属性
                .allowedMethods("GET","POST","DELETE","PUT")
                //允许的header属性
                .allowedHeaders("*")
                //跨域允许的时间
                .maxAge(3600);
    }
}
