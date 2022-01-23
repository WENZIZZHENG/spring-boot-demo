package com.example.canal.handler;

/**
 * <p>
 * canal数据处理接口
 * </p>
 *
 * @author MrWen
 */
public interface CanalEntryHandler<T> {

    /**
     * 新增
     *
     * @param t 新增数据
     */
    void insert(T t);


    /**
     * 修改
     *
     * @param before 修改前数据
     * @param after  修改后数据
     */
    void update(T before, T after);


    /**
     * 删除
     *
     * @param t 删除数据
     */
    void delete(T t);
}
