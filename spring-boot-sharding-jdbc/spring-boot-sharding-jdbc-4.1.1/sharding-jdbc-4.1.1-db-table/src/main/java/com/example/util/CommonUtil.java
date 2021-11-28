package com.example.util;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * <p>
 * 通用工具类
 * </p>
 *
 * @author MrWen
 **/
public class CommonUtil {

    /**
     * 时间转年分格式
     *
     * @param date 2021-11-28 15:41:26
     * @return 2021
     */
    public static String date2YearStr(Date date) {
        return DateUtil.format(date, "yyyy");
    }

    /**
     * 时间转季度格式
     *
     * @param date 2021-11-28 15:41:26
     * @return 10_12
     */
    public static String date2QuarterStr(Date date) {
        int quarter = DateUtil.quarter(date);
        switch (quarter) {
            case 1:
                return "01_03";
            case 2:
                return "04_06";
            case 3:
                return "07_09";
            case 4:
                return "10_12";
            default:
                return "";
        }
    }
}
