# Spring Boot多线程

## 1 概述

以自定义线程池控制Spring Boot的多线程



**多线程使用：**

1. 在启动类加上**@EnableAsync**注解
2. 在对应的方法加上**@Async()**注解，也可以是【建议】**@Async("default")**，其中default就是配置类**ThreadPoolTaskConfig**对应的线程池



## 2 @Async失效原因及解决方案

原因：

1. 没有在@SpringBootApplication启动类当中添加注解@EnableAsync注解。
2. 异步方法使用注解@Async的返回值只能为void或者Future。
3. 没有走Spring的代理类。因为@Transactional和@Async注解的实现都是基于Spring的AOP，而AOP的实现是基于动态代理模式实现的。那么注解失效的原因就很明显了，有可能因为**调用方法的是对象本身而不是代理对象**，因为没有经过Spring容器。**【简而言之：不能由本类内其它函数调用，必须是外部使用者调用。】**

解决方案（基于第三点）

1. 从上下文中获取Bean
2. 使用懒加载自己注入自己
3. 示例：**ThreadService**





## 3 其它参考

线程池：Java 线程池学习总结、拿来即用的 Java 线程池最佳实践：https://snailclimb.gitee.io/javaguide/#/./docs/java/concurrent/java%E7%BA%BF%E7%A8%8B%E6%B1%A0%E5%AD%A6%E4%B9%A0%E6%80%BB%E7%BB%93?id=%e4%b8%83-%e7%ba%bf%e7%a8%8b%e6%b1%a0%e5%a4%a7%e5%b0%8f%e7%a1%ae%e5%ae%9a

