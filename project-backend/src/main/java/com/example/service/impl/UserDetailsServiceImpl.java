package com.example.service.impl;

import com.example.entity.Account;
import com.example.mapper.AccountMapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private AccountMapper accountMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!StringUtils.hasText(username))
            throw new RuntimeException("请输入用户名");
        Account account = accountMapper.findAccountByUsernameOrEmail(username);
        if (Objects.isNull(account))
            throw new RuntimeException("用户名或密码错误");
        return User
                .withUsername(username)
                .password(account.getPassword())
                .roles("user")
                .build();
    }
}
