package com.example.springboot.transcation;

import com.example.springboot.entity.UserInfo;
import com.example.springboot.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author gengshaohua.ext
 * @date 2021/10/28
 * @apiNote
 */
@Service
public class TranscationAnnotation {

    @Autowired
    private UserInfoService userInfoService;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = RuntimeException.class)
    public void insertUserTranscation(){

        //UserInfo userInfo = new UserInfo();
        //userInfo.setFirstName("a");
        //userInfo.setLastName("b");
        //userInfoService.insertUser(userInfo);

        UserInfo userInfo = new UserInfo();
        userInfo.setFirstName("b");
        userInfo.setLastName("c");
        userInfoService.insertUser(userInfo);
        throw new RuntimeException("aaaa");
    }



}
