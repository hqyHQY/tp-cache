package com.tp.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Auther: hequnayu
 * @Date: 2019/4/12 16:40
 * @Description: jedis集群可配置参数
 */
@Data
@Component
@ConfigurationProperties(prefix = "tp.redis.cluster")
public class RedisClusterConfigurationProperties extends RedisConfigurationProperties{
    /**
     * 主机端口地址，以逗号隔开
     */
    private String hostPort;

}
