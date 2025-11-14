package com.lyb.olinemusicserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement // 开启事务管理
@MapperScan("com.lyb.olinemusicserver.mapper")
public class OlineMusicServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OlineMusicServerApplication.class, args);
    }

}
