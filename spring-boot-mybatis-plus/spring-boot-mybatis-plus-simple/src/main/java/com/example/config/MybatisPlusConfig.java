package com.example.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * MP配置文件
 * </p>
 *
 * @author MrWen
 */
@Configuration
public class MybatisPlusConfig {


    /**
     * 配置mybatis-plus插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //配置分页插件
        interceptor.addInnerInterceptor(getPaginationInnerInterceptor());
        return interceptor;
    }


    /**
     * 获取分页插件
     * <p>
     * https://mp.baomidou.com/guide/interceptor-pagination.html#paginationinnerinterceptor
     */
    private PaginationInnerInterceptor getPaginationInnerInterceptor() {
        //如果出现：Mybatis Plus分页Page total为0问题：
        //原因：MybatisPlusConfig 分页插件配置类 和 SpringApplication启动类必须在同一级。

        PaginationInnerInterceptor pagination = new PaginationInnerInterceptor();
        //数据库类型(根据类型获取应使用的分页方言,参见 插件#findIDialect 方法) ,对于单一数据库类型来说,都建议配置该值,避免每次分页都去抓取数据库类型
        pagination.setDbType(DbType.MYSQL);
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        pagination.setOverflow(false);
        // 单页分页条数限制(默认无限制,参见 插件#handlerLimit 方法)
        pagination.setMaxLimit(500L);
        return pagination;
    }


}
