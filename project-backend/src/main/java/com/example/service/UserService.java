package com.example.service;

import com.example.entity.AccountInfo;

public interface UserService {
    void saveUserInfo(AccountInfo info);

    String updateEmail(String email, Integer uid);
}
