package com.example.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.example.dto.DemoData;
import com.example.service.IPoiService;
import com.example.util.CommonUtil;
import com.example.util.DataUtil;
import com.example.util.DownloadUtil;
import com.example.util.excel.ExcelParserUtil;
import com.example.util.excel.handler.DemoDataHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * poi示例
 * </p>
 *
 * @author MrWen
 * @since 2022-02-05 18:11
 */
@Slf4j
@Service
public class PoiServiceImpl implements IPoiService {

    /**
     * 时间格式化
     */
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void printExcel1() {
        //利用ApachePOI创建Excel表 核心 工作簿-->工作单-->字典
        //1  创建工作簿
        Workbook workbook = new HSSFWorkbook();
        //2  创建工作单
        Sheet sheet = workbook.createSheet();

        //3  创建字典（模版）  设置样式  数据
        //3.1 设置头标题
        Row titleRow = sheet.createRow(0);
        //3.1.1设置样式  字体样式  行高，列宽（所有列sheet）
        Cell titleRowCell = titleRow.createCell(0);
        //字体样式
        titleRowCell.setCellStyle(bigTitle(workbook));
        //行高
        titleRow.setHeightInPoints(36f); //磅

        //列宽
        /**
         * 参数一：列的索引
         * 参数二：列宽度值(字符数*256=磅)
         */
        sheet.setColumnWidth(0, 16 * 256);
        sheet.setColumnWidth(1, 26 * 256);
        sheet.setColumnWidth(2, 26 * 256);
        sheet.setColumnWidth(3, 16 * 256);

        //3.1.2设置头部数据
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));  //合并头单元格
        titleRowCell.setCellValue("poi测试数据");  //动态标题

        //3.2  设置表格头部数据 headerRow  1行 1-8列
        Row headerRow = sheet.createRow(1);
        String[] headerNames = {"字符串标题", "日期标题", "格式化日期标题", "数字标题"};
        for (int i = 0; i < headerNames.length; i++) {
            Cell headerRowCell = headerRow.createCell(i);
            headerRowCell.setCellStyle(bigTitle(workbook)); //设置样式
            headerRowCell.setCellValue(headerNames[i]);//填充数据
        }

        //3.3 创建字典（数据库输入导入格式）
        // 判断非空有数据
        List<DemoData> data = DataUtil.data();
        if (CollUtil.isNotEmpty(data)) {
            //循环行 从第二行开始
            for (int i = 0; i < data.size(); i++) {
                //获取集合中的数据
                DemoData vo = data.get(i);
                Row row = sheet.createRow(i + 2);
                //循环列  第二列到第九列
                for (int j = 0; j < 4; j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellStyle(text(workbook));
                    //字典
                    switch (j) {
                        case 0:
                            //字符串标题
                            cell.setCellValue(vo.getString());
                            break;
                        case 1:
                            //日期标题
                            cell.setCellValue(simpleDateFormat.format(vo.getDate()));
                            break;
                        case 2:
                            //格式化日期标题
                            cell.setCellValue(simpleDateFormat.format(vo.getDateFrom()));
                            break;
                        case 3:
                            //数字标题
                            cell.setCellValue(vo.getDoubleData());
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        //输出下载
        DownloadUtil.download(workbook, "poi普通导出.xls");
    }

    @Override
    public void printExcel2() {
        //工作  获取模版样式-->赋值

        //利用ApachePOI读取模块Excel表   核心 工作簿-->工作单-->字典
        //1.1 读取样式
        InputStream inputStream = new ClassPathResource("excel/tOUTPRODUCT.xlsx").getStream();
        //1.2  创建工作簿
        Workbook workbook;
        try {
            //xls格式--HSSFWorkbook
//            workbook = new HSSFWorkbook(inputStream);
            //xlsx格式--XSSFWorkbook
            workbook = new XSSFWorkbook(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("读取模板失败");
        }
        //2  获取工作单
        Sheet sheet = workbook.getSheetAt(0);
        //3  创建字典（模版）  设置样式  数据

        //3.1 设置头标题数据（修改数据）
        Row titleRow = sheet.getRow(0);
        Cell titleRowCell = titleRow.getCell(0);
        titleRowCell.setCellValue("poi模板测试" + System.currentTimeMillis());  //动态标题

        //3.2  获取插入数据对应的样式,存放在集合中（数据已经存在）
        Row headerRow = sheet.getRow(2);
        CellStyle[] styles = new CellStyle[3];
        for (int i = 0; i < 3; i++) {
            styles[i] = headerRow.getCell(i).getCellStyle();
        }

        //3.3 创建字典（数据库输入导入格式）
        List<DemoData> data = DataUtil.data();
        // 判断非空有数据
        if (CollUtil.isNotEmpty(data)) {
            //循环行 从第二行开始
            for (int i = 0; i < data.size(); i++) {
                //获取集合中的数据
                DemoData vo = data.get(i);
                Row row = sheet.createRow(i + 2);
                //循环列  第一列到第三列
                for (int j = 0; j < 3; j++) {
                    Cell cell = row.createCell(j);
                    //复制样式
                    cell.setCellStyle(styles[j]);
                    //字典
                    switch (j) {
                        case 0:
                            //字符串标题
                            cell.setCellValue(vo.getString());
                            break;
                        case 1:
                            //日期标题
                            cell.setCellValue(simpleDateFormat.format(vo.getDate()));
                            break;
                        case 2:
                            //数字标题
                            cell.setCellValue(vo.getDoubleData());
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        //输出下载
        DownloadUtil.download(workbook, "poi模板导出测试.xlsx");
    }

    @Override
    public void printExcel3() {
        //利用ApachePOI创建Excel表 核心 工作簿-->工作单-->字典
        //1  创建工作簿(和printExcel1一样，只是实现类从HSSFWorkbook改为SXSSFWorkbook)
        Workbook workbook = new SXSSFWorkbook();
        //2  创建工作单
        Sheet sheet = workbook.createSheet();

        //3  创建字典（模版）  设置样式  数据
        //3.1 设置头标题
        Row titleRow = sheet.createRow(0);
        //3.1.1设置样式  字体样式  行高，列宽（所有列sheet）
        Cell titleRowCell = titleRow.createCell(0);
        //字体样式
        titleRowCell.setCellStyle(bigTitle(workbook));
        //行高
        titleRow.setHeightInPoints(36f); //磅

        //列宽
        /**
         * 参数一：列的索引
         * 参数二：列宽度值(字符数*256=磅)
         */
        sheet.setColumnWidth(0, 16 * 256);
        sheet.setColumnWidth(1, 26 * 256);
        sheet.setColumnWidth(2, 26 * 256);
        sheet.setColumnWidth(3, 16 * 256);

        //3.1.2设置头部数据
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));  //合并头单元格
        titleRowCell.setCellValue("poi测试数据");  //动态标题

        //3.2  设置表格头部数据 headerRow  1行 1-8列
        Row headerRow = sheet.createRow(1);
        String[] headerNames = {"字符串标题", "日期标题", "格式化日期标题", "数字标题"};
        for (int i = 0; i < headerNames.length; i++) {
            Cell headerRowCell = headerRow.createCell(i);
            headerRowCell.setCellStyle(bigTitle(workbook)); //设置样式
            headerRowCell.setCellValue(headerNames[i]);//填充数据
        }

        //3.3 创建字典（数据库输入导入格式）
        // 判断非空有数据
        List<DemoData> data = DataUtil.data(1000000);
        if (CollUtil.isNotEmpty(data)) {
            //循环行 从第二行开始
            for (int i = 0; i < data.size(); i++) {
                //获取集合中的数据
                DemoData vo = data.get(i);
                Row row = sheet.createRow(i + 2);
                //循环列  第二列到第九列
                for (int j = 0; j < 4; j++) {
                    Cell cell = row.createCell(j);
                    //样式不能太多(最多64000)
//                    cell.setCellStyle(text(workbook));
                    //字典
                    switch (j) {
                        case 0:
                            //字符串标题
                            cell.setCellValue(vo.getString());
                            break;
                        case 1:
                            //日期标题
                            cell.setCellValue(simpleDateFormat.format(vo.getDate()));
                            break;
                        case 2:
                            //格式化日期标题
                            cell.setCellValue(simpleDateFormat.format(vo.getDateFrom()));
                            break;
                        case 3:
                            //数字标题
                            cell.setCellValue(vo.getDoubleData());
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        //输出下载
        DownloadUtil.download(workbook, "poi百万导出.xlsx");
    }

    @Override
    public void printCsv() {
       /*
        需要导包,这里easyexcel也有
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-csv</artifactId>
            <version>1.8</version>
        </dependency>
        */
        String fileName = "测试csv.csv";
        HttpServletResponse response = CommonUtil.getResponse();
        try {
            //响应设置
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);

            //csv设置
            Appendable out = new PrintWriter(response.getOutputStream());
            //头信息
            CSVPrinter printer = CSVFormat.DEFAULT.withHeader("字符串标题", "日期标题", "数字标题").print(out);
            //内容
            List<DemoData> dataList = DataUtil.data(1500000);
            for (DemoData data : dataList) {
                printer.printRecord(data.getString(), simpleDateFormat.format(data.getDate()), data.getDoubleData());
            }
            printer.flush();
            printer.close();
        } catch (Exception e) {
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            try {
//                response.getWriter().println(JSONUtil.toJsonStr(AjaxResult.failed()));
                response.getWriter().println("文件csv导出失败,原因：" + e.getMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    @Override
    public void importExcel(MultipartFile file) {
        //文件名
        String filename = file.getOriginalFilename();
        if (StrUtil.isBlank(filename)) {
            throw new RuntimeException("文件名不能为空！");
        }

        Workbook workbook;

        //判断上传文件格式  03版本以前还是07版本以后
        try {
            if (filename.endsWith(".xls")) {
                //03以前
                workbook = new HSSFWorkbook(file.getInputStream());
            } else if (filename.endsWith(".xlsx")) {
                //07以后
                workbook = new XSSFWorkbook(file.getInputStream());
            } else {
                //错误格式
                throw new RuntimeException("格式错误，请参考模版");
            }
        } catch (Exception e) {
            throw new RuntimeException("读取文件错误！");
        }

        //获取数据，并保存
        Sheet sheet = workbook.getSheetAt(0);
        int totalRows = sheet.getPhysicalNumberOfRows();
        //数据存储
        List<DemoData> dataList = new ArrayList<>(totalRows);

        //双循环保存信息  循环行数
        for (int i = 2; i < totalRows; i++) {
            Row row = sheet.getRow(i);
            //保存信息
            DemoData demoData = new DemoData();
            dataList.add(demoData);

            //循环列数，获取信息 看标准的格式
            for (int j = 0; j < 4; j++) {
                //判断值是否为空
                Cell cell = row.getCell(j);
                if (ObjectUtil.isNull(cell)) {
                    continue;
                }
                //查字典，赋值数据
                switch (j) {
                    case 0:
                        //字符串标题
                        demoData.setString(cell.getStringCellValue());
                        break;
                    case 1:
                        //日期标题
                        try {
                            demoData.setDate(simpleDateFormat.parse(cell.getStringCellValue()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        //格式化日期标题
                        try {
                            demoData.setDateFrom(simpleDateFormat.parse(cell.getStringCellValue()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        //数字标题
                        demoData.setDoubleData(cell.getNumericCellValue());
                        break;
                    default:
                        break;
                }
            }
        }
        dataList.forEach(System.out::println);
    }

    @Override
    public void importExcel2(MultipartFile file) {
        //可以新建，也可以注入
        ExcelParserUtil.parse(file, new DemoDataHandler());
    }


    /**
     * 大标题的样式
     *
     * @param wb 工作簿
     * @return 样式
     */
    private CellStyle bigTitle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);//字体加粗
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);                //横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);        //纵向居中
        return style;
    }


    /**
     * 文字样式
     *
     * @param wb 工作簿
     * @return 文字样式
     */
    private CellStyle text(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 10);

        style.setFont(font);

        style.setAlignment(HorizontalAlignment.LEFT);                //横向居左
        style.setVerticalAlignment(VerticalAlignment.CENTER);        //纵向居中
        style.setBorderTop(BorderStyle.THIN);                        //上细线
        style.setBorderBottom(BorderStyle.THIN);                    //下细线
        style.setBorderLeft(BorderStyle.THIN);                        //左细线
        style.setBorderRight(BorderStyle.THIN);                        //右细线

        return style;
    }
}
