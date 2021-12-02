package com.example.enums;

import com.example.config.base.IBaseEnum;

/**
 * <p>
 * 性别枚举
 * </p>
 *
 * @author MrWen
 **/
public enum GenderEnum implements IBaseEnum<Integer> {

    UNKNOWN(0, "未知"),
    BOY(1, "男"),
    GIRL(2, "女");

    private final int value;
    private final String desc;

    GenderEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getDescription() {
        return this.desc;
    }

    /**
     * mp会以这个为主
     */
    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "GenderEnum{" +
                "value=" + value +
                ", desc='" + desc + '\'' +
                '}';
    }
}
