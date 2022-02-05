//package com.example.util.excel.handler;
//
//import com.example.entity.Robot;
//import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
//import org.apache.poi.xssf.usermodel.XSSFComment;
//
///**
// * @Description: 自定义处理器
// * @Author: MrWen
// * @Create: 2021-10-08 22:19
// **/
//public class RobotHandler implements XSSFSheetXMLHandler.SheetContentsHandler {
//
//    //机器人昵称,头像
//    private Robot robot = null;
//
//    /**
//     * //每一行的开始
//     *
//     * @param rowIndex 代表的是每一个sheet的行索引
//     */
//    @Override
//    public void startRow(int rowIndex) {
//        if (rowIndex == 0) {
//            robot = null;
//        } else {
//            robot = new Robot();
//        }
//    }
//
//    /**
//     * 每一行的结束
//     *
//     * @param rowIndex 代表的是每一个sheet的行索引
//     */
//    @Override
//    public void endRow(int rowIndex) {
//        if (rowIndex != 0) {
//            System.out.println(robot);
//        }
//    }
//
//    /**
//     * 处理每一行的所有单元格
//     *
//     * @param cellName
//     * @param cellValue
//     * @param comment
//     */
//    @Override
//    public void cell(String cellName, String cellValue, XSSFComment comment) {
//        if (robot != null) {
//            String letter = cellName.substring(0, 1);  //每个单元名称的首字母 A  B  C
//            switch (letter) {
//                case "B": {
//                    robot.setNickname(cellValue);
//                    break;
//                }
//                case "C": {
//                    robot.setAvatar(cellValue);
//                    break;
//                }
//            }
//        }
//    }
//}
