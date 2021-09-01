package com.example.springboot.listener;

import com.example.springboot.event.SendMessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author gengshaohua.ext
 * @date 2021/9/1
 * @apiNote
 */
@Slf4j
@Component
public class SendMessageListener {

    @Async
    @EventListener(SendMessageEvent.class)
    public void handler() throws InterruptedException {
        log.info("发送短信");
    }

}
