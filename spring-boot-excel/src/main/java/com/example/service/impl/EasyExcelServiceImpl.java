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
    //todo csv问题

    @Override
    public void printExcel1() {
        //xlsx最多只能 1048576
//        List<DemoData> data = DataUtil.data();
        //超大数据量转csv格式导出
//        List<DemoData> data = DataUtil.data(1500000);
        List<DemoData> data = DataUtil.data(10);
        DownloadUtil.downloadExcel(DemoData.class, data, "easyExcel测试1.csv", "测试");
    }


    @Override
    public void printExcel2() {
//        //这里有个小bug，时间格式的样式会消失不见了.这个可以先自定义转为String，再输出
//        List<RobotExcelVo> excelVos = this.robotService.listExcelVo();
//        List<RobotExcelVo2> vo2s = excelVos.stream().map(vo -> {
//            RobotExcelVo2 vo2 = new RobotExcelVo2();
//            BeanUtil.copyProperties(vo, vo2);
//            vo2.setAvatarUrl(URLUtil.url(vo.getAvatar()));
//            return vo2;
//        }).collect(Collectors.toList());
//        DownloadUtil.downloadExcelByTemplate(vo2s, "EasyExcel模板.xls", "excel/EasyExcel模板.xlsx");
//
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
