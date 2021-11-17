package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.UserInfo;
import com.example.springboot.mapper.UserInfoMapper;
import com.example.springboot.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * @author gengshaohua.ext
 * @date 2021/10/28
 * @apiNote
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Override
    public void insertUser(UserInfo userInfo) {
        this.save(userInfo);
    }

    @Override
    public UserInfo getUserInfo(String firstName, String lastName) {
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getFirstName, firstName).eq(UserInfo::getLastName, lastName);
        return this.getOne(lambdaQueryWrapper);
    }

}
