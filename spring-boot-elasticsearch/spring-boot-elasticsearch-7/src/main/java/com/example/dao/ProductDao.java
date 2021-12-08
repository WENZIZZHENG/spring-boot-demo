package com.example.dao;

import com.example.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * spring data elasticsearch的ORM操作
 * </p>
 *
 * @author MrWen
 **/
@Repository
public interface ProductDao extends ElasticsearchRepository<Product, Long> {


    /**
     * 通过分类名称查询结果
     *
     * @param category 分类名称
     * @return 查询结果
     */
    List<Product> findByCategory(String category);

    /**
     * 分类及价格大于等于的结果集
     *
     * @param category 分类名称
     * @param price    大于等于的商品价格
     * @return 查询结果
     */
    List<Product> findByCategoryAndPriceGreaterThanEqual(String category, Double price);


    /**
     * 分页查询，分类及价格大于等于的结果集
     *
     * @param category 分类名称
     * @param price    大于等于的商品价格
     * @param pageable 分页查询
     * @return 分页结果
     */
    Page<Product> findByCategoryAndPriceGreaterThanEqual(String category, Double price, Pageable pageable);

}
