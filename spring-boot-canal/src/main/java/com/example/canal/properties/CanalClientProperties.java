package com.example.canal.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * canal客户端属性
 * </p>
 *
 * @author MrWen
 */
@Data
@ConfigurationProperties(prefix = "canal")
public class CanalClientProperties {
    /**
     * ip地址
     */
    private String host = "127.0.0.1";

    /**
     * 端口
     */
    private Integer port = 11111;

    /**
     * 描述
     */
    private String destination = "example";

    /**
     * 账号
     */
    private String username = "canal";

    /**
     * 密码
     */
    private String password = "canal";

    /**
     * 获取指定数量的数据
     */
    private Integer batchSize = 1000;

    /**
     * 是否开启ack确认
     * true: 如果永远无法消费，将会死循环！！
     * false: 消息会丢失，照成消息不同步
     * 建议：false，把错误信息收集，后续人工处理。
     */
    private Boolean acknowledgeMode = false;
}
