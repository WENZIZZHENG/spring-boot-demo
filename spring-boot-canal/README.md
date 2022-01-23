# canal

## 1 概述

阿里巴巴 MySQL binlog 增量订阅&消费组件

因为canal官方demo并没有给spring boot封装详情demo，所以这里就以spring boot封装以便处理数据。



**使用注意：**

获取表名，优先级从左到右  **@CanalTable**-->**@TableName**

canal拉取数据：以定时任务触发（启动类记得加**@EnableScheduling **），当然也可以像canal官方demo一样，用while一直循环获取



## 2 其它参考

官网文档：https://github.com/alibaba/canal/wiki

spring boot canal starter 易用的canal 客户端：https://github.com/NormanGyllenhaal/canal-client

基于canal的mysql和elasticsearch实时同步方案，支持增量同步和全量同步：https://github.com/starcwang/canal_mysql_elasticsearch_sync
