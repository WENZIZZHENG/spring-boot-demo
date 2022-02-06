package com.example.util.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.InputStream;

/**
 * <p>
 * 自定义Excel解析器
 * </p>
 *
 * @author MrWen
 **/
@Slf4j
public class ExcelParserUtil {

    /**
     * 解析(读取)
     *
     * @param in      文件流
     * @param handler 类型处理器,需要自定义
     */
    public static void parse(InputStream in, XSSFSheetXMLHandler.SheetContentsHandler handler) {
        //1.根据Excel获取OPCPackage对象
        try {
            OPCPackage pkg = OPCPackage.open(in);
            parse(pkg, handler);
        } catch (Exception e) {
            throw new RuntimeException("自定义Excel解析器出错啦,错误信息" + e.getMessage());
        }
    }

    /**
     * 解析(读取)
     *
     * @param file    文件
     * @param handler 类型处理器,需要自定义
     */
    public static void parse(File file, XSSFSheetXMLHandler.SheetContentsHandler handler) {
        //1.根据Excel获取OPCPackage对象
        try {
            OPCPackage pkg = OPCPackage.open(file, PackageAccess.READ);
            parse(pkg, handler);
        } catch (Exception e) {
            throw new RuntimeException("自定义Excel解析器出错啦,错误信息" + e.getMessage());
        }
    }

    /**
     * 解析(读取)
     *
     * @param file    文件信息
     * @param handler 类型处理器,需要自定义
     */
    public static void parse(MultipartFile file, XSSFSheetXMLHandler.SheetContentsHandler handler) {
        //1.根据Excel获取OPCPackage对象
        try {
            parse(file.getInputStream(), handler);
        } catch (Exception e) {
            throw new RuntimeException("自定义Excel解析器出错啦,错误信息" + e.getMessage());
        }
    }


    /**
     * 解析(读取)
     *
     * @param path    文件路径
     * @param handler 类型处理器,需要自定义
     */
    public static void parse(String path, XSSFSheetXMLHandler.SheetContentsHandler handler) {
        //1.根据Excel获取OPCPackage对象
        try {
            OPCPackage pkg = OPCPackage.open(path, PackageAccess.READ);
            parse(pkg, handler);
        } catch (Exception e) {
            throw new RuntimeException("自定义Excel解析器出错啦,错误信息" + e.getMessage());
        }
    }


    /**
     * 解析(读取)
     *
     * @param pkg
     * @param handler 类型处理器,需要自定义
     */
    public static void parse(OPCPackage pkg, XSSFSheetXMLHandler.SheetContentsHandler handler) {
        try {
            //2.创建XSSFReader对象
            XSSFReader reader = new XSSFReader(pkg);
            //3.获取SharedStringsTable对象
            SharedStringsTable sst = reader.getSharedStringsTable();
            //4.获取StylesTable对象
            StylesTable styles = reader.getStylesTable();
            XMLReader parser = XMLReaderFactory.createXMLReader();
            // 处理公共属性：Sheet名，Sheet合并单元格
            parser.setContentHandler(new XSSFSheetXMLHandler(styles, sst, handler, false));
            XSSFReader.SheetIterator sheets = (XSSFReader.SheetIterator) reader.getSheetsData();
            while (sheets.hasNext()) {
                InputStream sheetstream = sheets.next();
                InputSource sheetSource = new InputSource(sheetstream);
                try {
                    parser.parse(sheetSource);
                } finally {
                    sheetstream.close();
                }
            }
        } catch (Exception e) {
            log.error("自定义Excel解析器出错啦,错误信息{}", e.getMessage(), e);
            throw new RuntimeException("自定义Excel解析器出错啦,错误信息" + e.getMessage());
        } finally {
            try {
                pkg.close();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("关闭pkg出错啦");
            }
        }
    }
}
