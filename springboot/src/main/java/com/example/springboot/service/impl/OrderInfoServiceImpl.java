package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.OrderInfo;
import com.example.springboot.mapper.OrderInfoMapper;
import com.example.springboot.service.OrderInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author gengshaohua.ext
 * @date 2021/8/30
 * @apiNote
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void a() throws InterruptedException {
        OrderInfo orderInfo = this.getById(5);
        orderInfo.setStatus(1);
        orderInfo.setOrderStatus(1);
        this.updateById(orderInfo);
        //业务模拟处理10秒
        Thread.sleep(30000);
        orderInfo = this.getById(5);
        orderInfo.setStatus(2);
        orderInfo.setOrderStatus(2);
        this.updateById(orderInfo);
    }

    @Override
    public void b(){
        OrderInfo orderInfo = this.getById(5);

        orderInfo.setModelNo("!");
        orderInfo.setStatus(3);
        orderInfo.setOrderStatus(3);
        this.updateById(orderInfo);
    }

}
