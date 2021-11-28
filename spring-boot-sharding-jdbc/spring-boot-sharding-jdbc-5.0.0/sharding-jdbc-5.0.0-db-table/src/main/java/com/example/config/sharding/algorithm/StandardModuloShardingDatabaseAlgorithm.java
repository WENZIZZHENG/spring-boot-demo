package com.example.config.sharding.algorithm;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <p>
 * 分库，精确分片算法与范围分片算法
 * </p>
 *
 * @author MrWen
 **/
@Slf4j
public final class StandardModuloShardingDatabaseAlgorithm implements StandardShardingAlgorithm<Date> {


    private final static Date DATE_2022 = DateUtil.parseDate("2022-01-01");

    @Override
    public void init() {
        log.info("==================初始化StandardModuloShardingDatabaseAlgorithm成功==================");
    }

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

    /**
     * 范围分片算法类名称，用于BETWEEN >, <, >=, <=  （可选）
     *
     * @param databaseNames      实际数据库名 ds0,ds1
     * @param shardingValueRange 字库属性 PreciseShardingValue(logicTableName=t_goods, columnName=create_time, valueRange=[2020-10-28 15:41:26..2021-11-28 15:41:26])
     * @return 具体哪些表，多个
     */
    @Override
    public Collection<String> doSharding(final Collection<String> databaseNames, final RangeShardingValue<Date> shardingValueRange) {
        Range<Date> shardingKey = shardingValueRange.getValueRange();
/*
        有下面几种情况
        [2020-10-28 15:41:26..2021-11-28 15:41:26]
        [2020-10-28 15:41:26..+∞)
        (-∞..2021-11-28 15:41:26]
        。。。

       对于是否包含
         [  CLOSED    (   OPEN
        BoundType boundType1 = shardingKey.lowerBoundType();
         ]  CLOSED    )   OPEN
        BoundType boundType2 = shardingKey.upperBoundType();
*/
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = shardingKey.lowerEndpoint();
        } catch (Exception e) {
            //转换失败，表示无穷小
        }
        try {
            endDate = shardingKey.upperEndpoint();
        } catch (Exception e) {
            //转换失败，表示无穷大
        }

        //返回具体表结果
        Set<String> result = new LinkedHashSet<>();
        if (startDate != null && endDate != null) {
            // 用于BETWEEN 或 startDate <= create_time <= endDate
            //[2020-10-28 15:41:26..2021-11-28 15:41:26]
            if (startDate.compareTo(DATE_2022) >= 0) {
                result.add("ds1");
            } else if (endDate.compareTo(DATE_2022) < 0) {
                result.add("ds0");
            } else {
                result.add("ds0");
                result.add("ds1");
            }
        } else if (startDate != null) {
            // 用于 create_time >= startDate 或 create_time > startDate
            //[2020-10-28 15:41:26..+∞)
            if (startDate.compareTo(DATE_2022) >= 0) {
                result.add("ds1");
            } else {
                result.add("ds0");
                result.add("ds1");
            }
        } else if (endDate != null) {
            // 用于 create_time <= endDate 或 create_time < endDate
            //(-∞..2021-11-28 15:41:26]
            if (endDate.compareTo(DATE_2022) < 0) {
                result.add("ds0");
            } else {
                result.add("ds0");
                result.add("ds1");
            }
        }

        if (CollUtil.isEmpty(result)) {
            //获取不了具体库
            throw new UnsupportedOperationException();
        }
        return result;
    }

    @Override
    public String getType() {
        return "STANDARD_TEST_DB";
    }
}
