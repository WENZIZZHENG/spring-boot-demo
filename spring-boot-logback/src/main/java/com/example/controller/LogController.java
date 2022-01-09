package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * logback日志测试
 * </p>
 *
 * @author MrWen
 **/
@Slf4j
@RestController
@RequestMapping("/log")
public class LogController {


    @RequestMapping("/test")
    public void test() {
        log.debug("debug日志测试啦！");
        log.info("info日志测试啦！");
        log.warn("warn日志测试啦！");
        log.error("error日志测试啦！");
    }
}
