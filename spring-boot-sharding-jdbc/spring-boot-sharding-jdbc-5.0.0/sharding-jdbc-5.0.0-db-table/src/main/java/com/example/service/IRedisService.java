package com.example.service;

/**
 * <p>
 * redis相关的
 * </p>
 *
 * @author MrWen
 **/
public interface IRedisService {

    /**
     * 自增操作
     *
     * @param delta 自增步长
     */
    Long increment(String key, long delta);

}
