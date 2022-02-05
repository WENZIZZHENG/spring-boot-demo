package com.example.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * easyexcel示例
 * </p>
 *
 * @author MrWen
 **/
public interface IEasyExcelService {

    /**
     * 1普通excel xls格式导出 能支持xlsx，xls，csv格式导出
     */
    void printExcel1();

    /**
     * 2模板导出(数据填充)  能支持xlsx，xls，csv格式导出
     */
    void printExcel2();

    /**
     * 3excel导入  能支持xlsx，xls，csv格式
     * 参考：https://www.yuque.com/easyexcel/doc/read
     *
     * @param file excel文件（测试数据：printExcel1接口的导出结果即可）
     */
    void importExcel(MultipartFile file);
}
