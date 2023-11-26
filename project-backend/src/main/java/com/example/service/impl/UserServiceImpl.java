package com.example.service.impl;

import com.example.entity.AccountInfo;
import com.example.mapper.AccountMapper;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    AccountMapper userMapper;
    @Override
    public void saveUserInfo(AccountInfo info) {
        userMapper.saveInfo(info);
    }
}
