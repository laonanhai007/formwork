package com.example.service.impl;

import com.example.entity.AccountInfo;
import com.example.mapper.AccountMapper;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    AccountMapper userMapper;
    @Override
    public void saveUserInfo(AccountInfo info) {
        userMapper.saveInfo(info);
    }

    @Override
    public String updateEmail(String email, Integer uid) {
        if (Strings.isEmpty(email)){
            return "请填写邮箱信息再进行重置";
        }
        userMapper.updateEmailById(email,uid);
        return null;
    }
}
