package com.example.springboot.config;

import lombok.Data;

/**
 * @author gengshaohua.ext
 * @date 2021/8/19
 * @apiNote
 */
@Data
public class RedisSentinel {

    /**
     * 主机
     */
    private String master;

    /**
     * 节点
     */
    private String nodes;

    /**
     * 密码
     */
    private String password;


}
