package com.example.controller;

import com.example.service.IEasyExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * <p>
 * easyexcel示例
 * </p>
 *
 * @author MrWen
 **/
@RestController
@RequestMapping("/easy/excel")
@Api(tags = "easyExcel框架测试")
public class EasyExcelController {

    @Autowired
    private IEasyExcelService easyExcelService;

    /**
     * 支持xlsx，xls，csv格式导出
     */
    @GetMapping("/printExcel1")
    @ApiOperation("1普通excel导出")
    public void printExcel1() {
        easyExcelService.printExcel1();
    }

    @GetMapping("/printExcel2")
    @ApiOperation("2模板导出(数据填充)")
    public void printExcel2() {
        easyExcelService.printExcel2();
    }

    /**
     * file测试数据：printExcel1接口的导出结果即可
     * 参考：https://www.yuque.com/easyexcel/doc/read
     */
    @PostMapping("/importExcel")
    @ApiOperation("3excel导入")
    public String importExcel(@RequestParam("file") MultipartFile file) {
        easyExcelService.importExcel(file);
        return "success";
    }

}
