package com.example.config;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.Log;

/**
 * <p>
 * 自定义mybatis日志
 * </p>
 *
 * @author MrWen
 **/
@Slf4j
public class MybatisLog implements Log {

    /**
     * 配置类中是
     * protected Class<? extends Log> logImpl;
     * 不这样写会报错
     */
    public MybatisLog(String clazz) {
        // Do Nothing
    }

    /**
     * 是否调试,主要是sql执行
     */
    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    /**
     * 是否跟踪，主要是返回值（没啥用）
     */
    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public void error(String s, Throwable e) {
        log.error(s, e);
    }

    @Override
    public void error(String s) {
        log.error(s);
    }

    @Override
    public void warn(String s) {
        log.warn(s);
    }

    /**
     * sql执行
     */
    @Override
    public void debug(String s) {
        //排除太长以及其它无关的sql内容
        //"==>  Preparing: SELECT COUNT(*) FROM mall_admin WHERE deleted = 0 AND tenant_id = 1"
        //<==      Total: 1
        if (s.length() < 3000 && (StrUtil.startWith(s, "==>") || StrUtil.startWith(s, "<=="))) {
            log.debug(s);
        }
    }

    /**
     * 返回值，没啥用（太多了）
     */
    @Override
    public void trace(String s) {
        log.trace(s);
    }


}
