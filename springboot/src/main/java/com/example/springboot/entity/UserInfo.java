package com.example.springboot.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author gengshaohua.ext
 * @date 2021/10/28
 * @apiNote
 */
@Data
public class UserInfo implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 性
     */
    private String firstName;

    /**
     * 名
     */
    private String lastName;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 修改时间
     */
    private LocalDateTime updatedTime;

}
