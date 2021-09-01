package com.example.springboot.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * @author gengshaohua.ext
 * @date 2021/9/1
 * @apiNote
 */
public class SendMessageEvent extends ApplicationEvent {

    /**
     * 消息
     */
    private String msg;

    /**
     * 手机号
     */
    private String phone;

    public SendMessageEvent(Object source) {
        super(source);
    }
}
