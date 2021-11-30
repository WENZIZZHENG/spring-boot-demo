package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * mybatis-pluis 快速入门
 * </p>
 *
 * @author MrWen
 **/
@SpringBootApplication
@MapperScan("com.example.mapper")
public class MybatisPlusSimpleApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusSimpleApplication.class);
    }
}
