package com.example.springboot;

import com.example.springboot.service.OrderInfoService;
import com.example.springboot.transcation.TranscationAnnotation;
import com.example.springboot.transcation.TranscationManual;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author gengshaohua.ext
 * @date 2021/8/30
 * @apiNote
 */
public class TransactionTest extends SpringbootApplicationTests{

    @Autowired
    TranscationAnnotation transcationAnnotation;

    @Autowired
    TranscationManual transcationManual;

    @Test
    public void testTranscationAnnotation(){
        transcationManual.insertUserTranscationRequiresNew();
    }


}
