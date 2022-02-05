package com.example.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * poi示例
 * </p>
 *
 * @author MrWen
 * @since 2022-02-05 18:10
 */
public interface IPoiService {

    /**
     * 1普通excel xls格式导出
     */
    void printExcel1();

    /**
     * 2模板导出
     * xls格式--HSSFWorkbook
     * xlsx格式--XSSFWorkbook
     */
    void printExcel2();

    /**
     * 3.1百万数据打印输出,不能模板导出
     * 样式不能太多 用SXSSFWorkbook这个实体类  必须是.xlsx
     */
    void printExcel3();

    /**
     * 3.2百万数据打印输出csv格式
     */
    void printCsv();


    /**
     * excel导入
     *
     * @param file excel
     */
    void importExcel(MultipartFile file);

    /**
     * excel导入-百万数据
     *
     * @param file excel
     */
    void importExcel2(MultipartFile file);


}
