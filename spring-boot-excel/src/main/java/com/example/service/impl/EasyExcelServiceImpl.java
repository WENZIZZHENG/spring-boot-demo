package com.example.service.impl;

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

    //todo 图片导出

    @Override
    public void printExcel1() {
        //xls：数据量<=65500
        List<DemoData> data = DataUtil.data();
        //xlsx：65500<数据量<=1048500
//        List<DemoData> data = DataUtil.data(100000);
        //csv: 数据量>1048500
//        List<DemoData> data = DataUtil.data(1500000);
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
//        try {
//            EasyExcel.read(file.getInputStream(), RobotExcelVo.class, new RobotListener(robotService)).sheet().doRead();
//        } catch (Exception e) {
//            throw new ServiceException("读取文件失败啦！");
//        }
    }
}
