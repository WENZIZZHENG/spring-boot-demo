package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * <p>
 * 启动类
 * </p>
 *
 * @author MrWen
 **/
@EnableAsync//多线程
@SpringBootApplication
public class MultiThreadApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiThreadApplication.class);
    }

}
