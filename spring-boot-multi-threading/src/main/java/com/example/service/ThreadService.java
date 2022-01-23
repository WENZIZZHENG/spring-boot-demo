package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 多线程测试
 * </p>
 *
 * @author MrWen
 **/
@Slf4j
@Service
public class ThreadService implements ApplicationContextAware {

    /**
     * 上下文
     */
    private ApplicationContext applicationContext;

    @Lazy
    @Autowired
    private ThreadService threadService;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Async("default")
    public void test1() {
        log.info("test1--->ThreadId={},---->ThreadName={}", Thread.currentThread().getId(), Thread.currentThread().getName());

        //test2方法是同步，并不是异步。原因是：不能由本类内其它函数调用，必须是外部使用者调用。因为内部函数调用会出现代理绕过，从而无法执行异步，不会出错，会变成同步操作 （AOP动态代理）
        this.test2();

        //test3会异步，用上下文获取ThreadService的Bean，能被代理
        ThreadService threadServiceBean = applicationContext.getBean(ThreadService.class);
        threadServiceBean.test3();

        //test4会异步,通过Lazy也是能被代理
        threadService.test4();
    }

    @Async("default")
    public void test2() {
        log.info("test2--->ThreadId={},---->ThreadName={}", Thread.currentThread().getId(), Thread.currentThread().getName());
    }


    @Async("default")
    public void test3() {
        log.info("test3--->ThreadId={},---->ThreadName={}", Thread.currentThread().getId(), Thread.currentThread().getName());
    }

    @Async("default")
    public void test4() {
        log.info("test4--->ThreadId={},---->ThreadName={}", Thread.currentThread().getId(), Thread.currentThread().getName());
    }

}
