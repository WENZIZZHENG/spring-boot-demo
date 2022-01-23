package com.example.canal.job;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.Message;
import com.example.canal.properties.CanalClientProperties;
import com.example.canal.util.FieldUtil;
import com.example.canal.handler.CanalEntryHandler;
import com.example.canal.handler.CanalMessageHandler;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * canal定时任务，主要是监听日志变化
 * </p>
 *
 * @author MrWen
 */
@Slf4j
@Component
@EnableConfigurationProperties(CanalClientProperties.class)
public class CanalScheduling {

    @Autowired
    private CanalMessageHandler messageHandler;
    @Autowired
    private CanalClientProperties canalClientProperties;
    @Resource
    private CanalConnector canalConnector;

    @Scheduled(fixedDelay = 100)
    public void run() {
        try {
            Message message = canalConnector.getWithoutAck(canalClientProperties.getBatchSize());
            long batchId = message.getId();
            try {
                List<Entry> entries = message.getEntries();
                if (batchId != -1 && entries.size() > 0) {
                    entries.forEach(entry -> {
                        if (entry.getEntryType() == EntryType.ROWDATA) {
                            handlerEntry(entry);
                        }
                    });
                }
                canalConnector.ack(batchId);
            } catch (Exception e) {
                log.error("发送监听事件失败！不会滚！message={},错误原因={}", JSON.toJSONString(message), e.getMessage(), e);
                if (canalClientProperties.getAcknowledgeMode()) {
                    canalConnector.rollback(batchId);
                } else {
                    canalConnector.ack(batchId);
                }
            }
        } catch (Exception e) {
            log.error("canal_scheduled异常！", e);
        }
    }

    /**
     * 对handlerEntry的处理
     *
     * @param entry 信息
     */
    private void handlerEntry(Entry entry) {
        //获取表名
        String table = entry.getHeader().getTableName();

        CanalEntry.RowChange change;
        try {
            change = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
        } catch (InvalidProtocolBufferException e) {
            log.error("canalEntry_parser_error,根据CanalEntry获取RowChange失败！", e);
            return;
        }
        EventType eventType = entry.getHeader().getEventType();

        //table对应的实现类
        CanalEntryHandler handlerMap = messageHandler.getHandlerMap(table);


        List<CanalEntry.RowData> rowDatasList = change.getRowDatasList();
        for (CanalEntry.RowData rowData : rowDatasList) {
            Object afterObj = getAfterObj(table, rowData);
            Object beforeObj = getBeforeObj(table, rowData);
            try {
                switch (eventType) {
                    case INSERT:
                        handlerMap.insert(afterObj);
                        break;
                    case UPDATE:
                        handlerMap.update(beforeObj, afterObj);
                        break;
                    case DELETE:
                        handlerMap.delete(beforeObj);
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                //保存错误原因
                log.error("canal同步数据出错，原因::{}", e.getMessage(), e);
            }
        }
    }

    /**
     * 获取修改前的数据
     *
     * @param table   表
     * @param rowData 操作数据
     * @return 修改前的数据
     */
    @SneakyThrows
    private Object getBeforeObj(String table, CanalEntry.RowData rowData) {
        CanalEntryHandler entryHandler = messageHandler.getHandlerMap(table);
        Object beforeObj = ReflectUtil.newInstance(FieldUtil.getTableClass(entryHandler));
        List<CanalEntry.Column> beforeColumnsList = rowData.getBeforeColumnsList();
        for (CanalEntry.Column column : beforeColumnsList) {
            FieldUtil.setFieldValue(beforeObj, column.getName(), column.getValue());
        }
        return beforeObj;
    }

    /**
     * 获取修改后的数据
     *
     * @param table   表
     * @param rowData 操作数据
     * @return 修改后的数据
     */
    @SneakyThrows
    private Object getAfterObj(String table, CanalEntry.RowData rowData) {
        CanalEntryHandler entryHandler = messageHandler.getHandlerMap(table);
        Object afterObj = ReflectUtil.newInstance(FieldUtil.getTableClass(entryHandler));
        List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
        for (CanalEntry.Column column : afterColumnsList) {
            FieldUtil.setFieldValue(afterObj, column.getName(), column.getValue());
        }
        return afterObj;
    }
}
