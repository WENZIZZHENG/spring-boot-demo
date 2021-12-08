package com.eample.es;

import cn.hutool.json.JSONUtil;
import com.eample.BaseTest;
import com.example.dao.ProductDao;
import com.example.entity.Product;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * spring boot data elasticsearch 测试
 * </p>
 *
 * @author MrWen
 **/
public class SpringBootElasticSearch7Test extends BaseTest {

    /**
     * ElasticsearchRestTemple是ElasticsearchOperations的子类的子类
     * 在ES7.x以下的版本使用的是ElasticsearchTemple，7.x以上版本已弃用ElasticsearchTemple，使用ElasticsearchRestTemple替代
     */
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private ProductDao productDao;

    /**
     * 索引操作
     * 1 创建索引并增加映射配置
     */
    @Test
    public void createIndex() {
        //创建索引，系统初始化会自动创建索引
        System.out.println("创建索引");
    }

    /**
     * 索引操作
     * 1 删除索引
     */
    @Test
    public void deleteIndex() {
        //创建索引，系统初始化会自动创建索引
        IndexOperations indexOps = elasticsearchOperations.indexOps(Product.class);
        boolean flag = indexOps.delete();
        System.out.println("删除索引 = " + flag);
    }


    //======================================文档操作======================================

    /**
     * 新增
     */
    @Test
    public void save() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("华为手机");
        product.setCategory("手机");
        product.setPrice(2999.0);
        product.setImages("http://www.atguigu/hw.jpg");
        product.setCreateTime(new Date());
        productDao.save(product);
    }

    //批量新增
    @Test
    public void saveAll() {
        Date now = new Date();
        Calendar instance = Calendar.getInstance();
        instance.setTime(now);

        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setId((long) i);
            product.setTitle("[" + i + "]" + (i % 2 == 0 ? "小米" : "华为"));
            product.setCategory(i % 3 == 0 ? "手机" : "电脑");
            product.setPrice(1999.0 + i);
            product.setImages("http://www.atguigu/xm.jpg");
            //创建时间+1天
            instance.add(Calendar.DATE, 1);
            product.setCreateTime(instance.getTime());
            productList.add(product);
        }

        productDao.saveAll(productList);
    }

    //修改
    @Test
    public void update() {
        Product product = new Product();
        product.setId(2L);
        product.setTitle("华为手机");
        product.setCategory("手机");
        product.setPrice(4888.0);
        product.setImages("http://www.atguigu/xm.jpg");
        product.setCreateTime(new Date());
        productDao.save(product);
    }

    //删除
    @Test
    public void delete() {
        Product product = new Product();
        product.setId(1L);
        productDao.delete(product);
    }

    //根据id查询
    @Test
    public void findById() {
        //如果不存在会报错
        Product product1 = productDao.findById(1L).get();
        System.out.println(product1);

        //不存在，执行其它的，这里默认返回null
        Product product2 = productDao.findById(4L).orElseGet(() -> null);
        System.out.println(product2);
    }

    //查询所有
    @Test
    public void findAll() {
        Iterable<Product> products = productDao.findAll();
        for (Product product : products) {
            System.out.println(product);
        }
    }


    //分页查询
    @Test
    public void findByPageable() {
        //设置排序(排序方式，正序还是倒序，排序的id)
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        int currentPage = 0;//当前页，第一页从0开始，1表示第二页
        int pageSize = 5;//每页显示多少条
        //设置查询分页
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize, sort);
        //分页查询
        Page<Product> productPage = productDao.findAll(pageRequest);
        System.out.println("总页数 = " + productPage.getTotalPages());
        System.out.println("总数量 = " + productPage.getTotalElements());
        for (Product Product : productPage.getContent()) {
            System.out.println(Product);
        }
    }


    //======================================ORM操作======================================
    @Test
    public void testDao() {
        System.out.println("========================findByCategory========================");
        List<Product> productList1 = this.productDao.findByCategory("手机");
        System.out.println("productList1.size() = " + productList1.size());
        productList1.forEach(System.out::println);

        System.out.println("========================findByCategoryAndPriceGreaterThanEqual========================");
        List<Product> productList2 = this.productDao.findByCategoryAndPriceGreaterThanEqual("手机", 2006.0);
        System.out.println("productList2.size() = " + productList2.size());
        productList2.forEach(System.out::println);


        System.out.println("========================findByCategoryAndPriceGreaterThanEqual，自定义分页========================");
        //设置排序(排序方式，正序还是倒序，排序的id)
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        int currentPage = 0;//当前页，第一页从0开始，1表示第二页
        int pageSize = 2;//每页显示多少条
        //设置查询分页
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize, sort);
        Page<Product> productPage = this.productDao.findByCategoryAndPriceGreaterThanEqual("手机", 2006.0, pageRequest);
        System.out.println("总元素 = " + productPage.getTotalElements());
        System.out.println("总页数 = " + productPage.getTotalPages());
        productPage.getContent().forEach(System.out::println);
    }

    //======================================ORM操作======================================


    //===================================ElasticsearchOperations查询===================================
    /**
     * ElasticsearchOperations查询
     * 对关键字查询(TermQueryBuilder)
     * boolean查询(BoolQueryBuilder )
     * 范围（日期）查询(RangeQueryBuilder)
     * 当一次查询中有多个查询条件时，建议使用boolean查询，将其他查询条件通过BoolQueryBuilder的must、should、mustNot控制。
     */

    /**
     * term（关键字）查询
     * search(termQueryBuilder) 调用搜索方法，参数查询构建器对象
     */
    @Test
    public void termQuery() {
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", "小米");
        //这个方法被废弃
        Iterable<Product> products = productDao.search(termQueryBuilder);
        for (Product product : products) {
            System.out.println(product);
        }

        System.out.println("\t\n====================分界线=======================\t\n");


        //推荐的方法
        TermQueryBuilder termQueryBuilder2 = QueryBuilders
                //字段名,值
                .termQuery("title", "小米");
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(termQueryBuilder2)
                .build();
        SearchHits<Product> search = elasticsearchOperations.search(searchQuery,
                Product.class
        );
        for (SearchHit<Product> productSearchHit : search) {
            Product product = productSearchHit.getContent();
            System.out.println("product = " + product);
        }
    }


    /**
     * term查询加分页
     */
    @Test
    public void termQueryByPage() {
        //设置排序(排序方式，正序还是倒序，排序的id)
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        int currentPage = 0;
        int pageSize = 2;
        //设置查询分页
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize, sort);
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", "小米");
        Iterable<Product> products = productDao.search(termQueryBuilder, pageRequest);
        for (Product product : products) {
            System.out.println(product);
        }


        System.out.println("\t\n====================分界线=======================\t\n");


        //推荐的方法
        TermQueryBuilder termQueryBuilder2 = QueryBuilders
                //字段名,值
                .termQuery("title", "小米");
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(termQueryBuilder2)
                .withPageable(pageRequest)
                .build();
        SearchHits<Product> search = elasticsearchOperations.search(searchQuery,
                Product.class
        );
        System.out.println("search = " + search);
        //总数量(总命中)
        System.out.println("totalHits = " + search.getTotalHits());

        for (SearchHit<Product> productSearchHit : search) {
            System.out.println("\t\n productSearchHit = " + productSearchHit);
            Product product = productSearchHit.getContent();
            System.out.println("product = " + product);
        }
    }


    /**
     * boolean查询查询
     * BoolQueryBuilder主要方法
     * <p>
     * must（QueryBuilder）：必须满足的条件
     * should（QueryBuilder）：可能满足的条件
     * mustNot（QueryBuilder）：必须不满足的条件
     * minimumShouldMatch（x）：设置在可能满足的条件中，至少必须满足其中x条
     */
    @Test
    public void booleanQuery() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                //查询必须满足的条件
                .must(QueryBuilders.termQuery("category", "手机"))

                //查询可能满足的条件
                .should(QueryBuilders.termQuery("title", "华为"))
                .should(QueryBuilders.termQuery("title", "电脑"))
                // 设置在可能满足的条件中，至少必须满足其中1条
                .minimumShouldMatch(1)

                //必须不满足的条件,这个可以使用范围查询
                .mustNot(QueryBuilders.termQuery("price", 2999.0));

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();
        SearchHits<Product> search = elasticsearchOperations.search(searchQuery, Product.class);
        for (SearchHit<Product> searchHit : search) {
            System.out.println(searchHit.getContent());
        }
    }

    /**
     * range(范围)查询
     * 指定日期数据时可以使用RangeQueryBuilder
     * <p>
     * gte：大于等于
     * lte：小于等于
     * gt ：大于
     * lt ：小于
     * format：指定日期格式
     */
    @Test
    public void rangeQuery() {
        RangeQueryBuilder rangeQueryBuilder1 =
                QueryBuilders.rangeQuery("price")
                        // 大于等于
                        .gte(2003)
                        // 小于等于
                        .lte(2007);

        RangeQueryBuilder rangeQueryBuilder2 = QueryBuilders.rangeQuery("createTime")
                .gte("2021-11-29 18:52:37")
                .lte("2021-12-01 18:52:37")
                .format("yyyy-MM-dd HH:mm:ss");

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(rangeQueryBuilder1)
                .withQuery(rangeQueryBuilder2)
                .build();
        System.out.println(searchQuery.getQuery());

        SearchHits<Product> search = this.elasticsearchOperations.search(searchQuery, Product.class);
        for (SearchHit<Product> searchHit : search) {
            System.out.println(searchHit.getContent());
        }
    }

    /**
     * 聚合aggregations-聚合为桶
     * 桶就是分组，比如这里我们按照  category 进行分组：
     */
    @Test
    public void testBucket() {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                // 不查询任何结果
                // 1、添加一个新的聚合，聚合类型为terms，聚合名称为categories，聚合字段为category
                .addAggregation(AggregationBuilders.terms("categories").field("category"))
                .build();

        // 2、查询,获取聚合
        SearchHits<Product> search = this.elasticsearchOperations.search(query, Product.class);
        Aggregations aggregations = search.getAggregations();


        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        //
        /**
         * 7.x以前写法
         * StringTerms agg = aggregations.get("categories");
         * 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
         *         类型有  StringTerms  LongTerms  DoubleTerms 三种选择
         * 7.x中，报错中是无法转化成 StringTerms 类型.在之前的版本中是可以的, 但在 7 版本以上就不好使了.
         * 需要将 StringTerms 类型改为 Terms 类型.
         * 即：Terms terms = aggregations.get("categories");
         */
        Terms terms = aggregations.get("categories");
        // 3.2、获取桶
        List<? extends Terms.Bucket> buckets = terms.getBuckets();

        // 3.3、遍历
        buckets.forEach(bucket -> {
            // 3.4、获取桶中的key，即分类名称category
            System.out.println(bucket.getKeyAsString());
            // 3.5、获取桶中的文档数量
            System.out.println(bucket.getDocCount());
        });
    }

    /**
     * 聚合aggregations-嵌套聚合，求平均值
     */
    @Test
    public void testAggregations() {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                // 不查询任何结果
                // 1、添加一个新的聚合，聚合类型为terms，聚合名称为 categories，聚合字段为 category
                .addAggregation(AggregationBuilders.terms("categories").field("category")
                        //比较常用的一些度量聚合方式：  avg max min count sum top stats等  dateHistogram日期 dateRange日期范围
                        //其中 stats：同时返回avg、max、min、sum、count等
                        // 在分类category聚合桶内进行嵌套聚合(即度量(metrics))，求平均值
                        .subAggregation(AggregationBuilders.avg("priceAvg").field("price")))
                .build();

        // 2、查询,获取聚合
        SearchHits<Product> search = this.elasticsearchOperations.search(query, Product.class);
        Aggregations aggregations = search.getAggregations();

        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        //
        /**
         * 7.x以前写法
         * StringTerms agg = aggregations.get("categories");
         * 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
         *         类型有  StringTerms  LongTerms  DoubleTerms 三种选择
         * 7.x中，报错中是无法转化成 StringTerms 类型.在之前的版本中是可以的, 但在 7 版本以上就不好使了.
         * 需要将 StringTerms 类型改为 Terms 类型.
         * 即：Terms terms = aggregations.get("categories");
         */
        Terms terms = aggregations.get("categories");
        // 3.2、获取桶
        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        // 3.3、遍历
        buckets.forEach(bucket -> {
            System.out.println("bucket = " + JSONUtil.toJsonStr(bucket));
            // 3.4、获取桶中的key，即分类名称   3.5、获取桶中的文档数量
            System.out.println(bucket.getKeyAsString() + "，共" + bucket.getDocCount() + "个");

            // 3.6.获取子聚合结果： get("priceAvg")map对应key的值
            Avg avg = bucket.getAggregations().get("priceAvg");
            System.out.println("平均售价：" + avg.getValueAsString());
        });
    }


    //===================================ElasticsearchOperations查询===================================

    //======================================文档操作======================================
}
