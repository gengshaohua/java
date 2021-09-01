package com.example.springboot.controller;

import com.example.springboot.entity.OrderInfo;
import com.example.springboot.event.SendMessageEvent;
import com.example.springboot.service.OrderInfoService;
import com.example.springboot.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gengshaohua.ext
 * @date 2021/9/1
 * @apiNote
 */
@RestController
public class DemoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping("find")
    public R find(){
        OrderInfo byId = orderInfoService.getById("5");
        applicationContext.publishEvent(new SendMessageEvent("来源"));
        return R.ok("查询成功").setData(byId);
    }

}
