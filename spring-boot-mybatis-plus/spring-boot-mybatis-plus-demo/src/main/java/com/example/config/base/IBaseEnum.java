package com.example.config.base;

import com.baomidou.mybatisplus.annotation.IEnum;

import java.io.Serializable;


/**
 * <p>
 * MP基础枚举注
 * </p>
 *
 * @author MrWen
 **/
public interface IBaseEnum<T extends Serializable> extends IEnum<T> {

    /**
     * 获取描述
     *
     * @return 描述
     */
    String getDescription();
}
