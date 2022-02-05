//package com.example.util.excel.listener;
//
//import cn.hutool.core.bean.BeanUtil;
//import com.alibaba.excel.context.AnalysisContext;
//import com.alibaba.excel.event.AnalysisEventListener;
//import com.example.dto.excel.RobotExcelVo;
//import com.example.entity.Robot;
//import com.example.service.IRobotService;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @Description: 模板的读取类 有个很重要的点 RobotListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
// * @Author: MrWen
// * @Create: 2021-10-10 11:03
// **/
//@Slf4j
//public class RobotListener extends AnalysisEventListener<RobotExcelVo> {
//
//    /**
//     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
//     */
//    private static final int BATCH_COUNT = 100;
//    List<Robot> list = new ArrayList<>();
//
//
//    /**
//     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
//     */
//    private final IRobotService robotService;
//
//    /**
//     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
//     *
//     * @param robotService service
//     */
//    public RobotListener(IRobotService robotService) {
//        this.robotService = robotService;
//    }
//
//    /**
//     * 这个每一条数据解析都会来调用
//     *
//     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
//     * @param context 上下文
//     */
//    @Override
//    public void invoke(RobotExcelVo data, AnalysisContext context) {
//        log.info("解析到一条数据:{}", data);
//        Robot robot = new Robot();
//        BeanUtil.copyProperties(data, robot);
//        list.add(robot);
//        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
//        if (list.size() >= BATCH_COUNT) {
//            saveData();
//            // 存储完成清理 list
//            list.clear();
//        }
//    }
//
//    /**
//     * 所有数据解析完成了 都会来调用
//     *
//     * @param context 上下文
//     */
//    @Override
//    public void doAfterAllAnalysed(AnalysisContext context) {
//        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
//        saveData();
//        log.info("所有数据解析完成！");
//    }
//
//
//    /**
//     * 加上存储数据库
//     */
//    private void saveData() {
//        log.info("{}条数据，开始存储数据库！", list.size());
//        list.forEach(System.out::println);
//        // 如果是mybatis,尽量别直接调用多次insert,自己写一个mapper里面新增一个方法batchInsert,所有数据一次性插入
////        robotService.saveBatch(list);
//        log.info("存储数据库成功！");
//    }
//
//}
