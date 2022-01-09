# ShardingSphere-JDBC



## 1 概述

**1.1 ShardingSphere-JDBC**

定位为轻量级 Java 框架，在 Java 的 JDBC 层提供的额外服务。 它使用客户端直连数据库，以 jar 包形式提供服务，无需额外部署和依赖，可理解为增强版的 JDBC 驱动，完全兼容 JDBC 和各种 ORM 框架。

- 适用于任何基于 JDBC 的 ORM 框架，如：JPA, Hibernate, Mybatis, Spring JDBC Template 或直接使用 JDBC。
- 支持任何第三方的数据库连接池，如：DBCP, C3P0, BoneCP, Druid, HikariCP 等。
- 支持任意实现 JDBC 规范的数据库，目前支持 MySQL，Oracle，SQLServer，PostgreSQL 以及任何遵循 SQL92 标准的数据库。



详情参考： [官网]：https://shardingsphere.apache.org/index_zh.html

## 2 注意事项

1.  版本问题：Sharding-JDBC不同版本之间配置文件差异很大，请确定好自身需要的版本
2.  常见的问题，[详情参考官网FAQ]：https://shardingsphere.apache.org/document/5.0.0/cn/reference/faq/



## 3 常见问题

### **3.1 分布式主键自增方案**

#### **3.1.1 雪花算法**

```tex
1：Sharding-jdbc有内置雪花算法：SNOWFLAKE，详情请看sharding-jdbc-5.0.0-db-table或sharding-jdbc-4.1.1-db-table配置文件
2：若有使用mybatis-plus，推荐优先使用mybatis-plus自定义的雪花算法。

两者之间的区域，对于实体类中的主键 @TableId(value = "id", type = IdType.AUTO)
1:使用 sharding-jdbc的雪花算法，IdType只能是AUTO，设置INPUT会报错(缺失时报错)，当插入对象ID不为空，仍让会自动填充，在某些场景下无法自定义id。
2:使用mybatis-plus的雪花算法 可以设为IdType.ASSIGN_ID,只有当插入对象ID 为空，才自动填充，在某些场景下可以自定义id。
```

**[雪花算法ID到前端之后精度丢失问题，全局解决方案](spring-boot-sharding-jdbc-5.0.0/sharding-jdbc-5.0.0-db-table/src/main/java/com/example/config/JacksonConfig.java)**

#### **3.1.2 redis自增主键**

```tex
1: 在对旧代码改造时，原来表主键为数据库自增且类型是Integer，不想对原来代码进行改动时推荐使用redis自增。
2：自定义主键生成器，详情请看sharding-jdbc-5.0.0-db-table或sharding-jdbc-4.1.1-db-table配置文件
3: 实现了 KeyGenerateAlgorithm 接口，也配置了 Type，但是自定义的分布式主键依然不生效？
Service Provider Interface (SPI) 是一种为了被第三方实现或扩展的 API，除了实现接口外，还需要在 META-INF/services 中创建对应文件来指定 SPI 的实现类，JVM 才会加载这些服务。
具体的 SPI 使用方式，请大家自行搜索。
与分布式主键 KeyGenerateAlgorithm 接口相同，其他 ShardingSphere 的扩展功能也需要用相同的方式注入才能生效。
```

[4.1.1版本参考](spring-boot-sharding-jdbc-4.1.1/sharding-jdbc-4.1.1-db-table/src/main/java/com/example/config/sharding/key/ShardingTableKeyGenerator.java)

[5.0.0版本参考](spring-boot-sharding-jdbc-5.0.0/sharding-jdbc-5.0.0-db-table/src/main/java/com/example/config/sharding/key/ShardingTableKeyGenerator.java)



### **3.2 mybatis-plus更新时，触发分片字段怎么办**

方案一: mybatis-plus更新前，把分片字段设为null。默认null时会跳过set。
方案二: 使用自定义[mybatis拦截器](spring-boot-sharding-jdbc-5.0.0/sharding-jdbc-5.0.0-db-table/src/main/java/com/example/config/MybatisPluginCustomInterceptor.java)，在拦截器中把分片字段set为空即可。（注意，mybatis-plus自动填功在这里是不能解决这个问题的）



### 3.3 spring-boot-starter-jooq与shardingsphere-jdbc整合问题

#### 3.3.1 spring-boot-starter-jooq生成的代码自带Schema处理

```tex
SQL示例:若Schema为test，表名为 t_user
select 'test'.'t_user'.'name','test'.'t_user'.'password' from 'test'.'t_user'
```

解决方案,设全局配置即可,参考

```java
@Component
public class JooqDao {

    @Autowired
    DSLContext create;

    @PostConstruct
    private void init() {
        //去掉sql中的单引号,这个加上会导致新增时字段为空时，sql解析错误
//        create.settings().withRenderNameStyle(RenderNameStyle.AS_IS);
        //去掉Schema
        create.settings().withRenderSchema(false);
        System.out.println("==========================初始化DSLContext成功==========================");
    }
}
```

#### 3.3.2 生成删除的sql语句时自带表别名

SQL示例：delete t0 from order_item t0 where t0.order_id = ?

[sharding-jdbc 中的issues]：https://github.com/apache/shardingsphere/issues/13273

解决方案，直接使用**5.0.0**以上版本



### 3.4 [为什么配置了某个数据连接池的 spring-boot-starter（比如 druid）和 shardingsphere-jdbc-spring-boot-starter 时，系统启动会报错？](https://shardingsphere.apache.org/document/5.0.0/cn/reference/faq/#1-jdbc-为什么配置了某个数据连接池的-spring-boot-starter比如-druid和-shardingsphere-jdbc-spring-boot-starter-时系统启动会报错)

``` tex
回答：

1. 因为数据连接池的 starter（比如 druid）可能会先加载并且其创建一个默认数据源，这将会使得 ShardingSphere-JDBC 创建数据源时发生冲突。
2. 解决办法为，去掉数据连接池的 starter 即可，ShardingSphere-JDBC 自己会创建数据连接池。
```





### 3.5 启动报错Correct the classpath of your application so that it contains a single, compatible version of com.google.common.collect.FluentIterable

**完整信息**

```te
***************************
APPLICATION FAILED TO START
***************************

Description:

An attempt was made to call a method that does not exist. The attempt was made from the following location:

    springfox.documentation.schema.DefaultModelDependencyProvider.dependentModels(DefaultModelDependencyProvider.java:79)

The following method did not exist:

    com.google.common.collect.FluentIterable.concat(Ljava/lang/Iterable;Ljava/lang/Iterable;)Lcom/google/common/collect/FluentIterable;

The method's class, com.google.common.collect.FluentIterable, is available from the following locations:

    jar:file:/D:/develop/maven/repository/com/google/guava/guava/18.0/guava-18.0.jar!/com/google/common/collect/FluentIterable.class

The class hierarchy was loaded from the following locations:

    com.google.common.collect.FluentIterable: file:/D:/develop/maven/repository/com/google/guava/guava/18.0/guava-18.0.jar


Action:

Correct the classpath of your application so that it contains a single, compatible version of com.google.common.collect.FluentIterable

Disconnected from the target VM, address: '127.0.0.1:6086', transport: 'socket'

Process finished with exit code 1
```

**原因**

guava版本冲突

**解决方案**

下载maven helper插件，查看冲突版本，去除低版本，保留高版本即可



## **4 其它参考**

1. [4.0.0-RC1版本Demo参考]：https://github.com/yinjihuan/sharding-jdbc

## 5 待补充

1. 复合分片，强制路由分片规则
2. 读写分离
3. 分布式事务
4. 数据加密
5. 分布式治理

