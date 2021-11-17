package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.UserInfo;

/**
 * @author gengshaohua.ext
 * @date 2021/10/28
 * @apiNote
 */
public interface UserInfoService extends IService<UserInfo> {

    void insertUser(UserInfo userInfo);

    UserInfo getUserInfo(String firstName, String lastName);

}
