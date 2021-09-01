package com.example.springboot;

import com.example.springboot.service.OrderInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author gengshaohua.ext
 * @date 2021/8/30
 * @apiNote
 */
public class TransactionTest extends SpringbootApplicationTests{

    @Autowired
    private OrderInfoService orderInfoService;

    @Test
    public void a() throws InterruptedException {
        orderInfoService.a();
    }

    @Test
    public void b(){
        orderInfoService.b();
    }


}
