package com.mcddhub.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * BackendApplication
 *
 * @version 1.0.0
 * @author: caobaoqi1029
 * @date: 2024/10/2 13:22
 */
@EnableScheduling
@SpringBootApplication
@MapperScan("com.mcddhub.backend.mapper")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class BackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}
