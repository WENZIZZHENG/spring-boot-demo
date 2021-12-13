# Spring Boot Demo 实践

**项目介绍**：

1. 个人学习资料整理，以及在工作中的最佳实践，初衷是能更快的帮助开发，运用到工作中。
2. 每个demo一般会有快速入门的简单版本，以及一个详细版。
3. 会尽可能的完善文档，便于某些功能在个性化需求时，能尽快的找出答案。

**如何支持**：

1. 点个`Star`并`Follow`我
2. 把该仓库分享给更多的朋友



## 项目结构

```
spring-boot-demo
  ├── spring-boot-elasticsearch                   #注意事项
  │   ├── spring-boot-elasticsearch-6             #elasticsearch 6.x版本
  │   └── spring-boot-elasticsearch-7             #elasticsearch 7.x版本
  ├── spring-boot-mybatis-plus                              #常见问题，注意事项,参考资料
  │   ├── spring-boot-mybatis-plus-demo                     #mybatis-plus 企业级应用
  │   ├── spring-boot-mybatis-plus-dynamic-datasourc-demo   #mybatis-plus 多数据源
  │   ├── spring-boot-mybatis-plus-generate                 #mybatis-plus 代码生成器，自定义模板
  │   └── spring-boot-mybatis-plus-simple                   #mybatis-plus 快速入门
  ├── spring-boot-sharding-jdbc                    #常见问题，注意事项,参考资料
  │   ├── spring-boot-sharding-jdbc-5.0.0
  │   │   ├── sharding-jdbc-5.0.0-simple-db-table  #分库分表快速入门版
  │   │   └── sharding-jdbc-5.0.0-db-table         #分库分表自定义分片规则，自定义自增主键生成器
  │   └── spring-boot-sharding-jdbc-4.1.1
  │       ├── sharding-jdbc-4.1.1-simple-db-table  #分库分表快速入门版
  │       └── sharding-jdbc-4.1.1-db-table         #分库分表自定义分片规则，自定义自增主键生成器
  ├── spring-boot-swagger                          #概述
  │   └── spring-boot-swagger-simple               #swagger快速入门版
  └── 其它  #正在补充中
```

**示例代码与概述**

- [spring-boot-elasticsearch](https://github.com/WENZIZZHENG/spring-boot-demo/tree/master/spring-boot-elasticsearch)

1.  elasticsearch 6.x，7.x版本示例
2.  原生elasticsearch操作
3.  Spring Data ElasticSearch操作示例,ORM操作，聚合操作

- [spring-boot-mybatis-plus示例](https://github.com/WENZIZZHENG/spring-boot-demo/tree/master/spring-boot-mybatis-plus)

1.  mybatis-plus 快速入门
2.  mybatis-plus 代码生成器，自定义模板
3.  mybatis-plus 多租户，自动分页，乐观锁，防止全表更新与删除，动态表名，sql性能规范，自定义ID生成器，数据填充，枚举等
4.  mybatis-plus 多数据源

- [spring-boot-sharding-jdbc示例](https://github.com/WENZIZZHENG/spring-boot-demo/tree/master/spring-boot-sharding-jdbc)

1.  分库分表快速入门（4.1.1版本以及5.0.0版本）
2.  自定义分库分表规则，自定义自增主键
3.  与mybatis-plus整合，解决更新时触发分片规则报错问题
4.  spring-boot-starter-jooq整合sharding-jdbc常见问题及解决方案

* [spring-boot-swagger示例](https://github.com/WENZIZZHENG/spring-boot-demo/tree/master/spring-boot-swagger)

