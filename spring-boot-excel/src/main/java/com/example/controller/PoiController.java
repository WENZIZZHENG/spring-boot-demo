package com.example.controller;

import com.example.service.IPoiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * poi示例
 * </p>
 *
 * @author MrWen
 **/
@RestController
@RequestMapping("/excel/poi")
@Api(tags = "原生poi示例")
public class PoiController {

    @Autowired
    private IPoiService poiService;


    @GetMapping("/printExcel1")
    @ApiOperation("1普通excel的xls格式导出")
    public void printExcel1() {
        poiService.printExcel1();
    }

    @GetMapping("/printExcel2")
    @ApiOperation("2模板导出")
    public void printExcel2() {
        poiService.printExcel2();
    }

    /**
     * 百万数据导出  xlsx最多只能 1048576
     * 样式最多64000
     */
    @GetMapping("/printExcel3")
    @ApiOperation("3.1百万数据打印输出")
    public void printExcel3() {
        poiService.printExcel3();
    }

    /**
     * 100万条以上数据，用excel会报错。 xlsx最多只能1048576
     * 1048576以上的，只能用csv
     */
    @GetMapping("/printCsv")
    @ApiOperation("3.2百万数据打印输出csv格式")
    public void printCsv() {
        poiService.printCsv();
    }


    /**
     * 数据量少的时候可以用。简单方便（10万以下呗）
     * 一次全部加载到内存中，数据量大时会直接OOM，反正加载不完（测试：100万数据直接导入）
     *
     * @param file 测试数据：printExcel1或printExcel3接口的导出结果即可
     */
    @PostMapping("/importExcel")
    @ApiOperation("4excel导入（少量数据）")
    public String importExcel(@RequestParam("file") MultipartFile file) {
        poiService.importExcel(file);
        return "success";
    }

    /**
     * 百万数据-大数据导入（只支持xlsx格式）
     * 解析是一条条解析，并不是全部加载到内存中。所以不会有OOM问题
     *
     * @param file 测试数据：printExcel1或printExcel3接口的导出结果即可
     */
    @PostMapping("/importExcel2")
    @ApiOperation("5excel导入-百万数据")
    public String importExcel2(@RequestParam("file") MultipartFile file) {
        poiService.importExcel2(file);
        return "success";
    }
}
