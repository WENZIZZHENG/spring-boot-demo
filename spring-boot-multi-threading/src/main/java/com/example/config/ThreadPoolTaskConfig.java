package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 * 统一线程池管理
 * </p>
 *
 * @author MrWen
 **/
@Configuration
public class ThreadPoolTaskConfig {

    /**
     * Async注解和事务一样，都是用AOP代理。事务有什么坑，Async也就有
     * 常见的坑；不能由本类内其它函数调用，必须是外部使用者调用。因为内部函数调用会出现代理绕过，从而无法执行异步，不会出错，会变成同步操作 （使用了线程池和动态代理）
     * <p>
     * ThreadPoolExecutor 3 个最重要的参数：
     * <p>
     * corePoolSize : 核心线程数线程数定义了最小可以同时运行的线程数量。
     * maximumPoolSize : 当队列中存放的任务达到队列容量的时候，当前可以同时运行的线程数量变为最大线程数。
     * workQueue: 当新任务来的时候会先判断当前运行的线程数量是否达到核心线程数，如果达到的话，新任务就会被存放在队列中。
     * <p>
     * ThreadPoolExecutor其他常见参数:
     * keepAliveTime:当线程池中的线程数量大于 corePoolSize 的时候，如果这时没有新的任务提交，核心线程外的线程不会立即销毁，而是会等待，直到等待的时间超过了 keepAliveTime才会被回收销毁；
     * unit : keepAliveTime 参数的时间单位。
     * threadFactory :executor 创建新线程的时候会用到。
     * <p>
     * handler :饱和策略。
     * ThreadPoolExecutor.AbortPolicy：抛出 RejectedExecutionException来拒绝新任务的处理。  默认
     * ThreadPoolExecutor.CallerRunsPolicy：调用执行自己的线程运行任务，也就是直接在调用execute方法的线程中运行(run)被拒绝的任务，如果执行程序已关闭，则会丢弃该任务。因此这种策略会降低对于新任务提交速度，影响程序的整体性能。如果您的应用程序可以承受此延迟并且你要求任何一个任务请求都要被执行的话，你可以选择这个策略。
     * ThreadPoolExecutor.DiscardPolicy： 不处理新任务，直接丢弃掉。
     * ThreadPoolExecutor.DiscardOldestPolicy： 此策略将丢弃最早的未处理的任务请求。
     */
    @Bean(name = "default")
    public ThreadPoolTaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //此方法返回可用处理器的虚拟机的最大数量; 不小于1
        int core = Runtime.getRuntime().availableProcessors();
        executor.setCorePoolSize(core);//设置核心线程数
        executor.setMaxPoolSize(core * 2 + 1);//设置最大线程数
        executor.setKeepAliveSeconds(3);//除核心线程外的线程存活时间
        executor.setQueueCapacity(40);//如果传入值大于0，底层队列使用的是LinkedBlockingQueue,否则默认使用SynchronousQueue
        executor.setThreadNamePrefix("thread-default-execute");//线程名称前缀
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());//设置拒绝策略，抛出 RejectedExecutionException来拒绝新任务的处理。
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());//设置拒绝策略，使用主线程
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());//设置拒绝策略，直接丢弃掉
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());//设置拒绝策略，丢弃最早的未处理的任务请求。
        return executor;
    }

    @Bean("async")
    public ThreadPoolTaskExecutor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //此方法返回可用处理器的虚拟机的最大数量; 不小于1
        executor.setCorePoolSize(20);//设置核心线程数
        executor.setMaxPoolSize(41);//设置最大线程数
        executor.setKeepAliveSeconds(30);//除核心线程外的线程存活时间
        executor.setQueueCapacity(100);//如果传入值大于0，底层队列使用的是LinkedBlockingQueue,否则默认使用SynchronousQueue
        executor.setThreadNamePrefix("thread-async-execute");//线程名称前缀
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());//使用主线程
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        return executor;
    }

}
