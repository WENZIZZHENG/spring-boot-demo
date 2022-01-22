package com.example.constant;

/**
 * <p>
 * 常量
 * </p>
 *
 * @author MrWen
 **/
public class Constants {
    /**
     * redis缓存 锁前缀
     */
    public static final String LOCK_SUFFIX = "MALL_LOCK";


    /**
     * 获取redis缓存key
     *
     * @param prefix 前缀
     * @param value  后缀
     * @return redis缓存key
     */
    public static String getRedisKey(String prefix, Object... value) {
        return String.format(prefix, value);
    }

}
