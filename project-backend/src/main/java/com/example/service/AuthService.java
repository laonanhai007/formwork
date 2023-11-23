package com.example.service;

import jakarta.servlet.http.HttpSession;

public interface AuthService {
    String sendValidateEmail(String email, String sessionId, boolean hasAccount);

    String validateAndRegister(String username, String password, String email, String code, String sessionId);

    String validateAndReset(String email, String code, String sessionId);

    String resetPassword(String password, HttpSession session);
}
