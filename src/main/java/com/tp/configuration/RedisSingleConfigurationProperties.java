package com.tp.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Auther: hequnayu
 * @Date: 2019/4/3 18:56
 * @Description: redis单节点可配置参数
 */
@Data
@Component
@ConfigurationProperties(prefix = "tp.redis.single")
public class RedisSingleConfigurationProperties extends RedisConfigurationProperties{
    /**
     * 主机地址
     */
    private String host;

    /**
     * 主机端口
     */
    private int port;

}
