package com.example.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @Description:
 * @Author: wenzizheng
 * @Create: 2020-08-10 10:15
 * @Version: 1.0
 **/
@Configuration
public class MybatisPlusConfig implements MetaObjectHandler {


    /**
     * 分页插件, 对于单一数据库类型来说,都建议配置该值,避免每次分页都去抓取数据库类型
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }


    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasSetter("createName")) {
            setFieldValByName("createName", "MrWen", metaObject);
        }
        if (metaObject.hasSetter("updateName")) {
            setFieldValByName("updateName", "MrWen", metaObject);
        }

        //有createTime属性的时候，才进行自动填充
        if (metaObject.hasSetter("createTime")) {
            setFieldValByName("createTime", new Date(), metaObject);
        }

        if (metaObject.hasSetter("updateTime")) {
            setFieldValByName("updateTime", new Date(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        if (metaObject.hasSetter("updateName")) {
            setFieldValByName("updateName", "MrWen", metaObject);
        }

        if (metaObject.hasSetter("updateTime")) {
            setFieldValByName("updateTime", new Date(), metaObject);
        }
    }
}
