package com.example.service.impl;

import com.example.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public boolean sendValidateEmail(String email) {
        return false;
    }
}
