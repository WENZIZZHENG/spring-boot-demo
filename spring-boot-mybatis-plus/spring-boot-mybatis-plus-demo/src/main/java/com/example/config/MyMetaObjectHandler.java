package com.example.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * <p>
 * MyBatis Plus 数据填充 元数据处理类 用于自动 createTime, updateTime, createUser, updateUser 等字段
 * </p>
 *
 * @author MrWen
 **/
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入时填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName("createName", "testInsertName", metaObject);
        setFieldValByName("updateName", "testInsertName", metaObject);
        setFieldValByName("createTime", new Date(), metaObject);
        setFieldValByName("updateTime", new Date(), metaObject);
    }

    /**
     * 修改时填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("updateName", "testUpdateName2", metaObject);
        setFieldValByName("updateTime", new Date(), metaObject);
    }
}
