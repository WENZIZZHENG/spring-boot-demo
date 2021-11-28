package com.example.config.sharding.algorithm;

import cn.hutool.core.collection.CollUtil;
import com.example.util.CommonUtil;
import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * <p>
 * 分表，精确分片算法与范围分片算法
 * </p>
 *
 * @author MrWen
 **/
@Slf4j
public final class StandardModuloShardingTableAlgorithm implements StandardShardingAlgorithm<Date> {


    private final Pattern pattern = Pattern.compile(".*?_(\\d{4}_.*)");

    @Override
    public void init() {
        log.info("===============================初始化StandardModuloShardingTableAlgorithm成功===============================");
    }

    /**
     * 自定义分表算法，用于=和IN，（必填）
     * 若缺失，思考一下：新增时，如何定位表？
     *
     * @param tableNames    实际分表名称 t_goods_2020_01_03  t_goods_2020_04_06 ...  t_goods_2021_10_12
     * @param shardingValue 字库属性 PreciseShardingValue(logicTableName=t_goods, columnName=create_time,value=2021-11-28 15:41:26)
     * @return 具体结果（那张表）
     */
    @Override
    public String doSharding(final Collection<String> tableNames, final PreciseShardingValue<Date> shardingValue) {
        //2021-11-28 15:41:26
        Date date = shardingValue.getValue();
        //2021
        String dateStr = CommonUtil.date2YearStr(date);
        //2021_10_12
        dateStr = dateStr + "_" + CommonUtil.date2QuarterStr(date);

        for (String tableName : tableNames) {
            if (tableName.endsWith(dateStr)) {
                return tableName;
            }
        }
        //不存在，抛出异常
        throw new UnsupportedOperationException();
    }

    /**
     * 范围分片算法类名称，用于BETWEEN >, <, >=, <=  （可选）
     *
     * @param tableNames    实际分表名称 t_goods_2020_01_03  t_goods_2020_04_06 ...  t_goods_2021_10_12
     * @param shardingValue 字库属性 PreciseShardingValue(logicTableName=t_goods, columnName=create_time, valueRange=[2020-10-28 15:41:26..2021-11-28 15:41:26])
     * @return 具体哪些表，多个
     */
    @Override
    public Collection<String> doSharding(final Collection<String> tableNames, final RangeShardingValue<Date> shardingValue) {
        Range<Date> shardingKey = shardingValue.getValueRange();
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
            //年 2020,2021
            String startYear = CommonUtil.date2YearStr(startDate);
            String endYear = CommonUtil.date2YearStr(endDate);
            //具体季度  10-12  10-12
            String startQuarter = CommonUtil.date2QuarterStr(startDate);
            String endQuarter = CommonUtil.date2QuarterStr(endDate);
            //2020_10-12,2021_10-12
            startYear = startYear + "_" + startQuarter;
            endYear = endYear + "_" + endQuarter;

            for (String tableName : tableNames) {
                //t_goods_2021_10_12，这里提取出 2021_10_12
                Matcher matcher = pattern.matcher(tableName);
                if (!matcher.matches()) {
                    break;
                }
                //2021_10_12
                String group1 = matcher.group(1);
                if (group1.compareTo(startYear) >= 0 && group1.compareTo(endYear) <= 0) {
                    result.add(tableName);
                }
            }
        } else if (startDate != null) {
            // 用于 create_time >= startDate 或 create_time > startDate
            //[2020-10-28 15:41:26..+∞)
            //年 2020
            String startYear = CommonUtil.date2YearStr(startDate);
            //具体季度  10-12
            String startQuarter = CommonUtil.date2QuarterStr(startDate);
            //2020_10-12
            startYear = startYear + "_" + startQuarter;

            for (String tableName : tableNames) {
                //t_goods_2021_10_12，这里提取出 2021_10_12
                Matcher matcher = pattern.matcher(tableName);
                if (!matcher.matches()) {
                    break;
                }
                //2021_10_12
                String group1 = matcher.group(1);
                if (group1.compareTo(startYear) >= 0) {
                    result.add(tableName);
                }
            }
        } else if (endDate != null) {
            // 用于 create_time <= endDate 或 create_time < endDate
            //(-∞..2021-11-28 15:41:26]
            //年 2021
            String endYear = CommonUtil.date2YearStr(endDate);
            //具体季度  10-12
            String endQuarter = CommonUtil.date2QuarterStr(endDate);
            //2021_10-12
            endYear = endYear + "_" + endQuarter;

            for (String tableName : tableNames) {
                //t_goods_2021_10_12，这里提取出 2021_10_12
                Matcher matcher = pattern.matcher(tableName);
                if (!matcher.matches()) {
                    break;
                }
                //2021_10_12
                String group1 = matcher.group(1);
                if (group1.compareTo(endYear) <= 0) {
                    result.add(tableName);
                }
            }
        }
        if (CollUtil.isEmpty(result)) {
            //获取不了具体表
            throw new UnsupportedOperationException();
        }
        return result;
    }

    /**
     * 分表-类型
     */
    @Override
    public String getType() {
        return "STANDARD_CREATE_TIME";
    }
}
