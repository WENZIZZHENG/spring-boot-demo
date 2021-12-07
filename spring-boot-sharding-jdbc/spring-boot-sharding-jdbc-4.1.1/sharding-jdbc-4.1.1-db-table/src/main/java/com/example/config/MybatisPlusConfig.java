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
 * <p>
 * MP配置文件
 * </p>
 *
 * @author MrWen
 */
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


    /***
     * mybatis-plus 自动填充-新增时触发
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        //为空时，才自动填充
        if (metaObject.getValue("createName") == null) {
            setFieldValByName("createName", "MrWen", metaObject);
        }
        if (metaObject.getValue("updateName") == null) {
            setFieldValByName("updateName", "MrWen", metaObject);
        }

        if (metaObject.getValue("createTime") == null) {
            setFieldValByName("createTime", new Date(), metaObject);
        }

        if (metaObject.getValue("updateTime") == null) {
            setFieldValByName("updateTime", new Date(), metaObject);
        }
    }

    /***
     * mybatis-plus 自动填充-修改时触发
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        //直接修改
        setFieldValByName("updateName", "MrWen", metaObject);

        setFieldValByName("updateTime", new Date(), metaObject);
    }
}
