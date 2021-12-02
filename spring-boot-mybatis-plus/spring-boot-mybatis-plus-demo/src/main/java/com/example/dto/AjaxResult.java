package com.example.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * <p>
 * 返回对象
 * </p>
 *
 * @author MrWen
 **/
@Data
@ApiModel("返回对象")
public class AjaxResult<T> {

    /**
     * 状态码
     */
    private String code;

    /**
     * 消息
     */
    private String msg;
    /**
     * 数据
     */
    private T data;


    public AjaxResult() {
    }

    public AjaxResult(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功返回结果
     */
    public static <T> AjaxResult<T> success() {
        return new AjaxResult<T>(Constants.SUCCESS_CODE, Constants.SUCCESS_MSG, null);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> AjaxResult<T> success(T data) {
        return new AjaxResult<T>(Constants.SUCCESS_CODE, Constants.SUCCESS_MSG, data);
    }


    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param message 提示信息
     */
    public static <T> AjaxResult<T> success(T data, String message) {
        return new AjaxResult<T>(Constants.SUCCESS_CODE, message, data);
    }


    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     */
    public static <T> AjaxResult<T> failed(String errorCode, String errorMsg) {
        return new AjaxResult<T>(errorCode, errorMsg, null);
    }

    /**
     * 失败返回结果
     *
     * @param errorMsg 错误信息
     */
    public static <T> AjaxResult<T> failed(String errorMsg) {
        return new AjaxResult<T>(Constants.ERROR_CODE, errorMsg, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> AjaxResult<T> failed() {
        return failed(Constants.ERROR_CODE);
    }

}
