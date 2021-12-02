package com.example.config;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.*;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * <p>
 * MP配置文件
 * </p>
 *
 * @author MrWen
 **/
@Configuration
public class MybatisPlusConfig {

    /**
     * 数据填充
     */
    @Bean
    public MyMetaObjectHandler myMetaObjectHandler() {
        return new MyMetaObjectHandler();
    }


    /**
     * 自定义ID生成器,这里需要考虑一个问题，多示例下如何保证机器码
     */
    @Bean
    public CustomIdGenerator customIdGenerator() {
        return new CustomIdGenerator();
    }

    /**
     * 插件配置
     * https://mp.baomidou.com/guide/interceptor.html
     * <p>
     * 多租户: TenantLineInnerInterceptor
     * 自动分页: PaginationInnerInterceptor
     * 乐观锁: OptimisticLockerInnerInterceptor
     * 防止全表更新与删除: BlockAttackInnerInterceptor
     * 动态表名: DynamicTableNameInnerInterceptor
     * sql性能规范: IllegalSQLInnerInterceptor
     * <p>
     * 如果个别表不需要拦截，可以加上 @InterceptorIgnore 拦截忽略注解
     * 该注解作用于 xxMapper.java 方法之上 各属性代表对应的插件 各属性不给值则默认为 false 设置为 true 忽略拦截 更多说明详见源码注释
     * <p>
     * 自定义拦截器-实现它 InnerInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //多租户  如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
        interceptor.addInnerInterceptor(getTenantLineInnerInterceptor());
        //配置分页插件
        interceptor.addInnerInterceptor(getPaginationInnerInterceptor());
        //乐观锁
        interceptor.addInnerInterceptor(getOptimisticLockerInnerInterceptor());
        //防止全表更新与删除,如果有多租户且符合租户条件，不会生效。 因为多了个条件  where tenant_id =1
        interceptor.addInnerInterceptor(getBlockAttackInnerInterceptor());
        //动态表名
//        interceptor.addInnerInterceptor(getDynamicTableNameInnerInterceptor());
        return interceptor;
    }


    /**
     * 多租户
     * 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
     * <p>
     * 说明:
     * 多租户 != 权限过滤,不要乱用,租户之间是完全隔离的!!!
     * 启用多租户后所有执行的method的sql都会进行处理.
     * 自写的sql请按规范书写(sql涉及到多个表的每个表都要给别名,特别是 inner join 的要写标准的 inner join)
     * <p>
     * https://mp.baomidou.com/guide/interceptor-tenant-line.html#tenantlineinnerinterceptor
     */
    private TenantLineInnerInterceptor getTenantLineInnerInterceptor() {
        return new TenantLineInnerInterceptor(new TenantLineHandler() {
            //获取租户 ID 值表达式，只支持单个 ID 值
            @Override
            public Expression getTenantId() {
                //实际情况，根据业务需求获取（配合权限，或本地线程ThreadLocal<String>操作）
                //无论是查询，还是新增修改还是删除，都会带上 tenant_id=new LongValue(1)这条件
                return new LongValue(1);
            }

            //获取租户字段名,默认字段: tenant_id,根据实际业务自定义返回
            @Override
            public String getTenantIdColumn() {
                return TenantLineHandler.super.getTenantIdColumn();
            }

            // 根据表名判断是否忽略拼接多租户条件,
            // 默认返回 false ,默认都要进行解析并拼接多租户条件
            // true:表示忽略，false:需要解析并拼接多租户条件
            @Override
            public boolean ignoreTable(String tableName) {
                //只有t_user表才忽略
                return "t_user".equalsIgnoreCase(tableName);
            }
        });
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

    /**
     * 乐观锁
     * 当要更新一条记录的时候，希望这条记录没有被别人更新
     * 乐观锁实现方式：
     * <p>
     * 取出记录时，获取当前version
     * 更新时，带上这个version
     * 执行更新时， set version = newVersion where version = oldVersion
     * 如果version不对，就更新失败
     * <p>
     * 说明:
     * 在实体类的字段上加上@Version注解
     * 支持的数据类型只有:int,Integer,long,Long,Date,Timestamp,LocalDateTime
     * 整数类型下 newVersion = oldVersion + 1
     * newVersion 会回写到 entity 中
     * 仅支持 updateById(id) 与 update(entity, wrapper) 方法
     * 在 update(entity, wrapper) 方法下, wrapper 不能复用!!!
     * <p>
     * https://mp.baomidou.com/guide/interceptor-optimistic-locker.html#optimisticlockerinnerinterceptor
     */
    private OptimisticLockerInnerInterceptor getOptimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }


    /**
     * 防止全表更新与删除（如果有多租户且符合租户条件，不会生效。 因为多了个条件  where tenant_id =1）
     * 针对 update 和 delete 语句 作用: 阻止恶意的全表更新删除
     * <p>
     * https://mp.baomidou.com/guide/interceptor-block-attack.html#blockattackinnerinterceptor
     */
    private BlockAttackInnerInterceptor getBlockAttackInnerInterceptor() {
        return new BlockAttackInnerInterceptor();
    }

    /**
     * 动态表名
     * <p>
     * 注意事项：
     * <p>
     * 原理为解析替换设定表名为处理器的返回表名，表名建议可以定义复杂一些避免误替换
     * 例如：真实表名为 user 设定为 mp_dt_user 处理器替换为 user_2019 等
     * <p>
     * https://mp.baomidou.com/guide/interceptor-dynamic-table-name.html#dynamictablenameinnerinterceptor
     */
    private DynamicTableNameInnerInterceptor getDynamicTableNameInnerInterceptor() {
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
        //高版本，3.4.3.4以及以上
/*        dynamicTableNameInnerInterceptor.setTableNameHandler((sql, tableName) -> {
            // 获取参数方法,根据实际业务,然后判断表明
            String year = "_2018";
            int random = new Random().nextInt(10);
            if (random % 2 == 1) {
                year = "_2019";
            }
            return tableName + year;
        });*/

        //key:表名，value：规则，通常与本地线程ThreadLocal配合
        Map<String, TableNameHandler> tableNameHandlerMap = new HashMap<>();
        //这里为不同的表设置对应表名处理器,也可以类实现TableNameHandler后注入
        //按天划分
        tableNameHandlerMap.put("user", ((sql, tableName) -> {
            String dateDay = DateUtil.format(new Date(), "yyyyMMdd");
            return tableName + "_" + dateDay;
        }));
        //按id取模分表，需要配合本地线程ThreadLocal
        tableNameHandlerMap.put("mall_admin", ((sql, tableName) -> {
            //简单点
            String year = "_2018";
            int random = new Random().nextInt(10);
            if (random % 2 == 1) {
                year = "_2019";
            }
            return tableName + year;
        }));
        dynamicTableNameInnerInterceptor.setTableNameHandlerMap(tableNameHandlerMap);
        return dynamicTableNameInnerInterceptor;
    }


}
