package com.example.config.sharding.algorithm;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;
import java.util.Date;

/**
 * <p>
 * 分库，精确分片算法类名称，用于=和IN
 * </p>
 *
 * @author MrWen
 **/
@Slf4j
public class PreciseModuloShardingDatabaseAlgorithm implements PreciseShardingAlgorithm<Date> {

    private final static Date DATE_2022 = DateUtil.parseDate("2022-01-01");

    /**
     * 自定义分表算法，用于=和IN，（必填）
     *
     * @param databaseNames 实际数据库名 ds0,ds1
     * @param shardingValue 字库属性 PreciseShardingValue(logicTableName=t_goods, columnName=create_time,value=2021-11-28 15:41:26)
     * @return 具体结果（那张表）
     */
    @Override
    public String doSharding(final Collection<String> databaseNames, final PreciseShardingValue<Date> shardingValue) {
        //2020-2021 在 ds0  2022-2023在ds1,这里直接写死，只做演示
        Date date = shardingValue.getValue();
        if (date.compareTo(DATE_2022) >= 0) {
            return "ds1";
        } else {
            return "ds0";
        }
    }
}
