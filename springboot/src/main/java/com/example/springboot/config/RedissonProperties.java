package com.example.springboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author gengshaohua.ext
 * @date 2021/8/19
 * @apiNote
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedissonProperties {

    /*
     * 数据库
     */
    private Integer database = 10;

    /**
     * 单机主机
     */
    private String host;

    /**
     * 单机端口号
     */
    private String port;

    /**
     * 密码
     */
    private String password;

    /**
     * 超时时间
     */
    private Integer timeout;

    /**
     * 重试次数
     */
    private Integer retryAttempts;

    /**
     * 重试间隔时间
     */
    private Integer retryInterval;

    /**
     * 连接间隔时间
     */
    private Integer pingConnectionInterval;

    /**
     * 集群
     */
    private RedisSentinel sentinel;

}
