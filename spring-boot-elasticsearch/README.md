# ElasticSearch

## 1 注意事项

### 1.1 版本选择

参考地址：https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#preface.versions

|                  Spring Data Release Train                   |                  Spring Data Elasticsearch                   | Elasticsearch |                       Spring Framework                       |                         Spring Boot                          |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :-----------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| 2021.1 (Q)[[1](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_1)] | 4.3.x[[1](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_1)] |    7.15.2     | 5.3.x[[1](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_1)] | 2.5 .x[[1](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_1)] |
|                       2021.0 (Pascal)                        |                            4.2.x                             |    7.12.0     |                            5.3.x                             |                            2.5.x                             |
|                       2020.0 (Ockham)                        |                            4.1.x                             |     7.9.3     |                            5.3.2                             |                            2.4.x                             |
|                           Neumann                            |                            4.0.x                             |     7.6.2     |                            5.2.12                            |                            2.3.x                             |
|                            Moore                             |                            3.2.x                             |    6.8.12     |                            5.2.12                            |                            2.2.x                             |
| Lovelace[[2](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_2)] | 3.1.x[[2](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_2)] |     6.2.2     |                            5.1.19                            |                            2.1.x                             |
| Kay[[2](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_2)] | 3.0.x[[2](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_2)] |     5.5.0     |                            5.0.13                            |                            2.0.x                             |
| Ingalls[[2](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_2)] | 2.1.x[[2](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_2)] |     2.4.0     |                            4.3.25                            |                            1.5.x                             |

 **类型（Type）**

在一个索引中，你可以定义一种或多种类型。

一个类型是你的索引的一个逻辑上的分类/分区，其语义完全由你来定。通常，会为具有一组共同字段的文档定义一个类型。不同的版本，类型发生了不同的变化

| 版本 | Type                                           |
| ---- | ---------------------------------------------- |
| 5.x  | 支持多种type                                   |
| 6.x  | 只能有一种type                                 |
| 7.x  | 默认不再支持自定义索引类型（默认类型为：_doc） |

### 1.2 中文分词

elasticsearch-analysis-ik：https://github.com/medcl/elasticsearch-analysis-ik



### 1.3 Mysql数据同步

[alibaba](https://github.com/alibaba)/canal：https://github.com/alibaba/canal

