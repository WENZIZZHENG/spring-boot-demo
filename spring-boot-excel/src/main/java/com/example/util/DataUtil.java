package com.example.util;

import com.example.dto.DemoData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 获取导出模拟数据
 * </p>
 *
 * @author MrWen
 * @since 2022-02-05 13:01
 */
public class DataUtil {

    /**
     * 获取数据（默认长度10）
     *
     * @return 对应长度的数据
     */
    public static List<DemoData> data() {
        return data(10);
    }

    /**
     * 获取数据
     *
     * @param size list长度
     * @return 对应长度的数据
     */
    public static List<DemoData> data(int size) {
        List<DemoData> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDateFrom(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
}
