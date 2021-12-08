package com.example.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * <p>
 * spring data elasticsearch的Entity对象
 * </p>
 *
 * @author MrWen
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "product", shards = 3, replicas = 1)//indexName 索引的名称（必填项）  shards 分片，默认1   replicas 备份，默认1
@ApiModel("商品")
public class Product {


    /**
     * 映射数据说明：
     * type:
     *       Text类型
     *          text：可分词
     *          keyword：不可分词，数据会作为完整字段进行匹配
     *       Numerical：数值类型
     *          基本数据类型: byte、short、integer、long、float、double、half_float
     *          浮点数的高精度类型: scaled_float
     *       Date：日期类型
     *       Array：数组类型
     *       Object：对象
     * index: 是否索引，默认为true，也就是说你不进行任何配置，所有字段都会被索引。
     *      true：字段会被索引，则可以用来进行搜索
     *      false：字段不会被索引，不能用来搜索
     * store: 否将数据进行独立存储，默认为false(获取独立存储的字段要比从_source中解析快得多，但是也会占用更多的空间，所以要根据实际业务需求来设置。)
     * analyzer: 分词器
     *      内置分词器: 地址： https://www.elastic.co/guide/en/elasticsearch/reference/current/analysis-analyzers.html
     *          standard（默认）: 它提供了基于语法的标记化（基于Unicode文本分割算法），适用于大多数语言
     *          simple: 功能强于WhitespaceAnalyzer,首先会通过非字母字符来分割文本信息，然后将词汇单元统一为小写形式。该分析器会去掉数字类型的字符。
     *          whitespace: 仅仅是去除空格，对字符没有lowcase化,不支持中文； 并且不对生成的词汇单元进行其他的规范化处理。
     *          stop: StopAnalyzer的功能超越了SimpleAnalyzer，在SimpleAnalyzer的基础上增加了去除英文中的常用单词（如the，a等），也可以更加自己的需要设置常用单词；不支持中文
     *          keyword: KeywordAnalyzer把整个输入作为一个单独词汇单元，方便特殊类型的文本进行索引和检索。针对邮政编码，地址等文本信息使用关键词分词器进行索引项建立非常方便。
     *          pattern: 一个pattern类型的analyzer可以通过正则表达式将文本分成"terms"(经过token Filter 后得到的东西 )。接受如下设置:
     *                  一个 pattern analyzer 可以做如下的属性设置:
     *                  lowercaseterms是否是小写. 默认为 true 小写.pattern正则表达式的pattern,
     *                  默认是 \W+.flags正则表达式的flagsstopwords一个用于初始化stop filter的需要stop 单词的列表.默认单词是空的列表
     *          language: 一个用于解析特殊语言文本的analyzer集合。（ arabic,armenian, basque, brazilian, bulgarian, catalan, cjk, czech, danish, dutch, english, finnish, french,galician, german, greek, hindi, hungarian, indonesian, irish, italian, latvian, lithuanian, norwegian,persian, portuguese, romanian, russian, sorani, spanish, swedish, turkish, thai.）
     *                    可惜没有中文。不予考虑
     *          snowball: 一个snowball类型的analyzer是由standard tokenizer和standard filter、lowercase filter、stop filter、snowball filter这四个filter构成的。
     *                      snowball analyzer 在Lucene中通常是不推荐使用的。
     *          Custom: 是自定义的analyzer。允许多个零到多个tokenizer，零到多个 Char Filters. custom analyzer 的名字不能以 "_"开头.
     *          fingerprint:
     *      IK分词器:
     *          ik_max_word：会将文本做最细粒度的拆分
     *          ik_smart： 会将文本做最粗粒度的拆分
     *
     */

    /**
     * 必须有id,这里的id是全局唯一的标识，等同于es中的"_id"
     */
    @Id
    @ApiModelProperty("商品唯一标识")
    private Long id;

    @ApiModelProperty("商品名称")
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;

    @ApiModelProperty("分类名称")
    @Field(type = FieldType.Keyword)
    private String category;

    @ApiModelProperty("商品价格")
    @Field(type = FieldType.Double)
    private Double price;

    @ApiModelProperty("图片地址")
    @Field(type = FieldType.Keyword, index = false)
    private String images;

    /**
     * 时间格式:地址:https://www.elastic.co/guide/en/elasticsearch/reference/current/mapping-date-format.html
     * <p>
     * date or strict_date  yyyy-MM-dd
     * date_hour_minute_second or strict_date_hour_minute_second   yyyy-MM-dd'T'HH:mm:ss
     * basic_date  yyyyMMdd
     * <p>
     * 自定义
     *
     * @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
     */
    @ApiModelProperty("创建时间")
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
