package com.example.controller;

import com.example.service.IEasyExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


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
     * 能支持xlsx，xls，csv格式导出
     * <p>
     * 超大数据量推荐使用原生这个，效率高很多（csv也是）
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


    @PostMapping("/importExcel")
    @ApiOperation("4excel导入")
    public String importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        easyExcelService.importExcel(file);
        return "success";
    }

}
