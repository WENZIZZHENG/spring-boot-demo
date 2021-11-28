package com.example.config.sharding.algorithm;

import com.example.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;
import java.util.Date;


/**
 * <p>
 * 分表，精确分片算法类名称，用于=和IN
 * </p>
 *
 * @author MrWen
 **/
@Slf4j
public class PreciseModuloShardingTableAlgorithm implements PreciseShardingAlgorithm<Date> {

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
}
