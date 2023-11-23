package com.example.service;

public interface AuthService {
    String sendValidateEmail(String email,String sessionId);

    String validateAndRegister(String username, String password, String email, String code,String sessionId);
}
