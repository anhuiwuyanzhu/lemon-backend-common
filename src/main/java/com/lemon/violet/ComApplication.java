package com.lemon.violet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
public class ComApplication {
    public static void main(String[] args) {
        SpringApplication.run(ComApplication.class,args);
    }
}
