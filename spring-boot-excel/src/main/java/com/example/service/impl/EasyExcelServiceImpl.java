package com.example.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.example.dto.DemoData;
import com.example.service.IEasyExcelService;
import com.example.util.DataUtil;
import com.example.util.DownloadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * easyexcel示例
 * </p>
 *
 * @author MrWen
 * @since 2022-02-05 12:58
 */
@Slf4j
@Service
public class EasyExcelServiceImpl implements IEasyExcelService {

    @Override
    public void printExcel1() {
        //xls：数据量<=65500
        List<DemoData> data = DataUtil.data();
        //xlsx：65500<数据量<=1048500
//        List<DemoData> data = DataUtil.data(100000);
        //csv: 数据量>1048500
//        List<DemoData> data = DataUtil.data(1300000);
        DownloadUtil.downloadExcel(DemoData.class, data, "easyExcel测试1", "测试");
    }


    @Override
    public void printExcel2() {
        List<DemoData> data = DataUtil.data();
        DownloadUtil.downloadExcelByTemplate(data, "easyExcel模板导出", "excel/EasyExcel模板.xls");
//        DownloadUtil.downloadExcelByTemplate(data, "easyExcel模板导出", "excel/EasyExcel模板.xlsx");
    }


    @Override
    public void importExcel(MultipartFile file) {
        //参考：https://www.yuque.com/easyexcel/doc/read

        // 写法1：JDK8+ ,不用额外写一个DemoDataListener
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 这里每次会读取3000条数据 然后返回过来 直接调用使用数据就行
        try {
            EasyExcel.read(file.getInputStream(), DemoData.class,
                            new PageReadListener<DemoData>(dataList -> {
                                for (DemoData demoData : dataList) {
                                    log.info("读取到一条数据{}", JSONUtil.toJsonStr(demoData));
                                }
                            }))
                    //这里不指定类型，会默认xlsx。xls和csv都会失效
                    .excelType(DownloadUtil.getExcelType(file.getOriginalFilename()))
                    .sheet().doRead();
        } catch (Exception e) {
            log.error("读取文件失败啦！,原因:{}", e.getMessage(), e);
            throw new RuntimeException("读取文件失败啦！");
        }
    }
}
