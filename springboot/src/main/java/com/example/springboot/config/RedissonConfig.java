package com.example.springboot.config;

import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson.JSON;
import com.example.springboot.lock.DistributedLockTemplate;
import com.example.springboot.lock.SingleDistributedLockTemplate;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient getRedisson(RedissonProperties redissonProperties) {
        Config config = new Config();

        if (redissonProperties.getSentinel() != null && redissonProperties.getSentinel().getNodes() != null) {
            String[] nodes = redissonProperties.getSentinel().getNodes().split(",");
            List<String> collect = Arrays.stream(nodes).map(item -> "redis://" + item).collect(Collectors.toList());
            String[] strArray = collect.toArray(new String[collect.size()]);
            log.info("当前集群环境：" + JSON.toJSONString(strArray));
            SentinelServersConfig sentinelServersConfig = config.useSentinelServers()
                    .setDatabase(redissonProperties.getDatabase())
                    .setPingConnectionInterval(redissonProperties.getPingConnectionInterval())
                    .setMasterName(redissonProperties.getSentinel().getMaster())
                    .addSentinelAddress(strArray)
                    .setTimeout(redissonProperties.getTimeout())
                    .setRetryAttempts(redissonProperties.getRetryAttempts())
                    .setRetryInterval(redissonProperties.getRetryInterval());
            if (CharSequenceUtil.isNotBlank(redissonProperties.getPassword())) {
                sentinelServersConfig.setPassword(redissonProperties.getPassword());
            }
        } else {
            SingleServerConfig singleServerConfig =
                    config.useSingleServer()
                            .setDatabase(redissonProperties.getDatabase())
                            .setPingConnectionInterval(redissonProperties.getPingConnectionInterval())
                            .setAddress("redis://" + redissonProperties.getHost() + ":" + redissonProperties.getPort());
            if (CharSequenceUtil.isNotBlank(redissonProperties.getPassword())) {
                singleServerConfig.setPassword(redissonProperties.getPassword());
            }
        }
        return Redisson.create(config);
    }

    @Bean
    DistributedLockTemplate distributedLockTemplate(RedissonClient redissonClient) {
        return new SingleDistributedLockTemplate(redissonClient);
    }

}
