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
     * 样式不能太多（最多64000） 用SXSSFWorkbook这个实体类  必须是.xlsx
     */
    void printExcel3();

    /**
     * 3.2百万数据打印输出csv格式
     */
    void printCsv();


    /**
     * excel导出（少量数据）
     * 数据量少的时候可以用。简单方便（10万以下呗）
     * 一次全部加载到内存中，数据量大时会直接OOM，反正加载不完（测试：100万数据直接导入）
     *
     * @param file 测试数据：printExcel1或printExcel3接口的导出结果即可
     */
    void importExcel(MultipartFile file);

    /**
     * 百万数据-大数据导入（只支持xlsx格式）
     * 解析是一条条解析，并不是全部加载到内存中。所以不会有OOM问题
     *
     * @param file 测试数据：printExcel1或printExcel3接口的导出结果即可
     */
    void importExcel2(MultipartFile file);


}
