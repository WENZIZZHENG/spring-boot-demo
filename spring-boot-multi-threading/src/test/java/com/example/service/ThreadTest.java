package com.example.service;

import com.example.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * <p>
 * 多线程测试
 * </p>
 *
 * @author MrWen
 **/
@Slf4j
public class ThreadTest extends BaseTest {

    @Autowired
    private ThreadService threadService;

    @Resource(name = "default")
    private ThreadPoolTaskExecutor executor;

    @Test
    public void test01() throws InterruptedException {
        log.info("测试类test01--->ThreadId={},---->ThreadName={}", Thread.currentThread().getId(), Thread.currentThread().getName());

        threadService.test1();

        //先睡一会，避免多线程未执行完，test类就关闭了
        Thread.sleep(30000);
    }

    /**
     * 执行多线程，带返回值
     */
    @Test
    public void test02() throws Exception {
        log.info("测试类test02--->ThreadId={},---->ThreadName={}", Thread.currentThread().getId(), Thread.currentThread().getName());

        //多线程测试-有返回值
        Callable<String> stringCallable = () -> String.format("多线程有返回值测试 ThreadId=%s,---->ThreadName=%s", Thread.currentThread().getId(), Thread.currentThread().getName());
        FutureTask<String> futureTask = new FutureTask<>(stringCallable);
        executor.submit(futureTask);
        System.out.println("futureTask.get() = " + futureTask.get());

        //先睡一会，避免多线程未执行完，test类就关闭了
        Thread.sleep(30000);
    }


}
