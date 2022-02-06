package com.example.util.excel.handler;

import com.example.dto.DemoData;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

import java.text.SimpleDateFormat;

/**
 * <p>
 * 自定义处理器
 * </p>
 *
 * @author MrWen
 **/
public class DemoDataHandler implements XSSFSheetXMLHandler.SheetContentsHandler {

    /**
     * 时间格式化
     */
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private DemoData demoData = null;

    /**
     * //每一行的开始
     *
     * @param rowIndex 代表的是每一个sheet的行索引
     */
    @Override
    public void startRow(int rowIndex) {
        //根据实际需求，这里演示跳过第一第二行
        if (rowIndex == 0 || rowIndex == 1) {
            demoData = null;
        } else {
            demoData = new DemoData();
        }
    }

    /**
     * 每一行的结束
     *
     * @param rowIndex 代表的是每一个sheet的行索引
     */
    @Override
    public void endRow(int rowIndex) {
        //根据实际需求，这里演示跳过第一第二行
        if (rowIndex != 0 && rowIndex != 1) {
            System.out.println(demoData);
        }
    }

    /**
     * 处理每一行的所有单元格
     *
     * @param cellName  每个单元名称的首字母 A  B  C
     * @param cellValue 每个单元的值
     * @param comment   单元格的注释
     */
    @Override
    public void cell(String cellName, String cellValue, XSSFComment comment) {
        if (demoData != null) {
            String letter = cellName.substring(0, 1);
            switch (letter) {
                case "A":
                    demoData.setString(cellValue);
                    break;
                case "B":
                    try {
                        demoData.setDate(simpleDateFormat.parse(cellValue));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "C":
                    try {
                        demoData.setDateFrom(simpleDateFormat.parse(cellValue));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "D":
                    demoData.setDoubleData(Double.valueOf(cellValue));
                    break;
                default:
                    break;
            }
        }
    }
}
