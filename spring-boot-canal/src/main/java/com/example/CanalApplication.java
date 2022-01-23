package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>
 * 启动类
 * </p>
 *
 * @author MrWen
 **/
@EnableScheduling//这里以定时任务触发，当然也可以想canal官方demo一样，用while一直循环获取
@SpringBootApplication
public class CanalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CanalApplication.class);

    }
}
