//package com.example.controller;
//
//import com.example.config.dto.AjaxResult;
//import com.example.service.IExcelPoiService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
///**
// * <p>
// * poi示例
// * </p>
// *
// * @author MrWen
// **/
//@RestController
//@RequestMapping("/excel/poi")
//@Api(tags = "原生poi示例")
//public class PoiController {
//
//    @Autowired
//    private IExcelPoiService excelPoiService;
//
//
//    @GetMapping("/printExcel1")
//    @ApiOperation("1普通excel xls格式导出")
//    public void printExcel1() {
//        excelPoiService.printExcel1();
//    }
//
//    @GetMapping("/printExcel2")
//    @ApiOperation("2模板导出")
//    public void printExcel2() {
//        excelPoiService.printExcel2();
//    }
//
//    /**
//     * 百万数据导出  xlsx最多只能 1048576
//     * 请求路径/excel/poi/printExcel3,请求方法GET 一共耗时15989 : ms
//     * 请求路径/easy/excel/printExcel1,请求方法GET 一共耗时181262 : ms
//     * 超大数据量推荐使用原生这个，效率高一点
//     */
//    @GetMapping("/printExcel3")
//    @ApiOperation("3.1百万数据打印输出")
//    public void printExcel3() {
//        excelPoiService.printExcel3();
//    }
//
//    /**
//     * 100万条以上数据，用excel会报错。 xlsx最多只能1048576
//     * 1048576以上的，只能用csv
//     */
//    @GetMapping("/printCsv")
//    @ApiOperation("3.2百万数据打印输出csv格式")
//    public void printCsv() {
//        excelPoiService.printCsv();
//    }
//
//
//    @PostMapping("/importExcel")
//    @ApiOperation("4excel导入")
//    public AjaxResult<String> importExcel(@RequestParam("file") MultipartFile file) {
//        excelPoiService.importExcel(file);
//        return AjaxResult.success();
//    }
//
//    @PostMapping("/importExcel2")
//    @ApiOperation("5excel导入-百万数据")
//    public AjaxResult<String> importExcel2(@RequestParam("file") MultipartFile file) {
//        excelPoiService.importExcel2(file);
//        return AjaxResult.success();
//    }
//}
