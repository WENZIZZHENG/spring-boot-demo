package com.example.config;

import com.example.entity.Goods;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>
 * mybatis拦截器
 * 全局拦截数据库创建和更新
 * <p>
 * Signature 对应 Invocation 构造器, type 为 Invocation.Object, method 为 Invocation.Method, args 为 Invocation.Object[]
 * method 对应的 update 包括了最常用的 insert/update/delete 三种操作, 因此 update 本身无法直接判断sql为何种执行过程
 * args 包含了其余多有的操作信息, 按数组进行存储, 不同的拦截方式有不同的参数顺序, 具体看type接口的方法签名, 然后根据签名解析, 参见官网
 * </p>
 *
 * @author MrWen
 **/
@Component
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
@Slf4j
public class MybatisPluginCustomInterceptor implements Interceptor {


    @Override
    //@SuppressWarnings("unchecked")
    public Object intercept(Invocation invocation) throws Throwable {
        // 根据签名指定的args顺序获取具体的实现类
        // 1. 获取MappedStatement实例, 并获取当前SQL命令类型
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType commandType = ms.getSqlCommandType();

        // 2. 获取当前正在被操作的类, 有可能是Java Bean, 也可能是普通的操作对象, 比如普通的参数传递
        Object parameter = invocation.getArgs()[1];
        // 获取拦截器指定的方法类型, 通常需要拦截 update
        String methodName = invocation.getMethod().getName();
        log.info("com.example.config.mybatis.MybatisPluginCustomInterceptor, methodName; {}, commandType: {}", methodName, commandType);


        if (parameter instanceof Map) {
            //  3. @Param 等包装类
            //更新时指定某些字段的最新数据值
            if (commandType.equals(SqlCommandType.UPDATE)) {
                Map map = (Map) parameter;
                Object et = map.get("et");
                Object param1 = map.get("param1");
                //这里对分片实体类进行操作，当然也可以通过 JSON操作，具体看实际业务
                if (et instanceof Goods && param1 instanceof Goods) {
                    Goods etAdmin = (Goods) et;
                    Goods param1Admin = (Goods) param1;
                    //修改时，分片字段为空。后续mybatis-plus插件会自动过滤null字段
                    etAdmin.setCreateTime(null);
                    param1Admin.setCreateTime(null);
                }
            }
        }
        return invocation.proceed();
    }
}
