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
     * 4excel导入  能支持xlsx，xls，csv格式导出
     *
     * @param file excel文件
     */
    void importExcel(MultipartFile file);
}
