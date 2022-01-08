package com.exanmple;

import com.example.RocketMQProducerApplication;
import com.example.dto.BatchDto;
import com.example.dto.OrderPaidEvent;
import com.example.dto.OrderStep;
import com.example.service.IRocketMQService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 测试类
 * </p>
 *
 * @author MrWen
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RocketMQProducerApplication.class)
public class RocketMQProducerTest {
    @Autowired
    private IRocketMQService rocketMQService;


    /**
     * 发送同步消息
     */
    @Test
    public void sendMessage() {
        for (int i = 0; i < 10; i++) {
            //广播消费端下的同步消息
            rocketMQService.sendMessage("Consumer_Broadcast", "广播同步消息" + i);
            //集群消费端下的同步消息
            rocketMQService.sendMessage("Consumer_Cluster", "集群同步消息" + i);
//            rocketMQService.sendMessage("Consumer_Cluster", null, i + "", "集群同步消息" + i);
        }
    }

    /**
     * 发送同步消息-Tag过滤模式
     */
    @Test
    public void sendMessageByTag() {
        rocketMQService.sendMessage("Consumer_Tag", "tag1", "过滤同步消息tag1");
        rocketMQService.sendMessage("Consumer_Tag", "tag2", "过滤同步消息tag2");
        rocketMQService.sendMessage("Consumer_Tag", "tag3", "过滤同步消息tag3");
    }

    /**
     * 发送同步消息-发送同步消息-SQL92模式
     */
    @Test
    public void sendMessageBySql() {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put("a", i);
            map.put("b", i % 2 == 0 ? "sql" : "notSql");
            rocketMQService.sendMessageBySql("Consumer_SQL", map, "SQL92模式消息  map=" + map);
        }
    }


    /**
     * 发送异步消息
     */
    @SneakyThrows
    @Test
    public void sendAsyncMessage() {
        for (int i = 0; i < 10; i++) {
            rocketMQService.sendAsyncMessage("Consumer_Cluster", "集群异步消息" + i, new SendCallback() {
                //发送成功
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("集群异步消息发送成功啦！sendResult={}", sendResult);
                }

                //发送失败
                @Override
                public void onException(Throwable e) {
                    log.error("集群异步消息发送失败啦！原因={}", e.getMessage(), e);
                }
            });
        }

        //先睡20秒，避免还没发送完毕就关闭了
        TimeUnit.SECONDS.sleep(20L);
    }


    /**
     * 发送单向消息
     */
    @Test
    public void sendOneway() {
        for (int i = 0; i < 10; i++) {
            rocketMQService.sendOneway("Consumer_Cluster", "集群单向消息" + i);
        }
    }


    /**
     * 发送批量消息（小批量）
     */
    @Test
    public void sendBatchMessage() {
        //演示发送实体类型
        List<BatchDto> batchDtoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            batchDtoList.add(
                    BatchDto.builder()
                            .id(i)
                            .message("发送批量消息Dto类型" + i)
                            .build());
        }
        rocketMQService.sendBatchMessage("Consumer_Batch", batchDtoList);
        log.info("=================发送Consumer_Batch主题完毕=================");

        //演示发送String类型
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            stringList.add("发送批量消息String类型" + i);
        }
//        rocketMQService.sendBatchMessage("Consumer_Cluster","A",null,stringList);
        rocketMQService.sendBatchMessage("Consumer_Cluster", stringList);
        log.info("=================发送Consumer_Cluster完毕=================");
    }


    /**
     * 发送批量消息（大批量，超过4MB）
     */
    @Test
    @SneakyThrows
    public void sendBatchMessage2() {
        //演示发送实体类型
        List<BatchDto> batchDtoList = new ArrayList<>(1000000);
        for (int i = 0; i < 1000000; i++) {
            batchDtoList.add(
                    BatchDto.builder()
                            .id(i)
                            .message("发送批量消息Dto类型" + i)
                            .build());
        }
        rocketMQService.sendBatchMessage("Consumer_Batch", batchDtoList);
        log.info("=================发送Consumer_Batch主题完毕=================");

        Thread.sleep(200000);
    }


    /**
     * 发送延时消息
     */
    @Test
    public void sendDelayLevel() {
        this.rocketMQService.sendDelayLevel("Consumer_Cluster", "集群延时消息", 4);
    }

    /**
     * 发送顺序消息-分区有序
     */
    @Test
    public void sendInOrder() {
        // 订单列表
        List<OrderStep> orderList = this.buildOrders();

        //循环一下，增加测试样本
//        for (int i = 0; i < 10; i++) {
        for (OrderStep orderStep : orderList) {
            this.rocketMQService.sendInOrder("Consumer_InOrder", orderStep, orderStep.getOrderId() + "");
        }
//        }
    }

    /**
     * 发送事务消息（一定要做幂等性处理（其实所有消息都要做幂等性。。））
     */
    @Test
    @SneakyThrows
    public void sendMessageInTransaction() {
        OrderPaidEvent build = OrderPaidEvent.builder()
                .orderId("123")
                .msg("事务消息-开始支付")
                .paidMoney(new BigDecimal(2))
                .build();
        this.rocketMQService.sendMessageInTransaction("Consumer_Transaction", build, "test");

        //先睡200秒，避免还没发送完毕就关闭了
        TimeUnit.SECONDS.sleep(200L);
    }


    /**
     * 生成模拟订单数据
     */
    private List<OrderStep> buildOrders() {
        List<OrderStep> orderList = new ArrayList<>();

        OrderStep orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111065L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103117235L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111065L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103117235L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111065L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("推送");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103117235L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        return orderList;
    }

}
