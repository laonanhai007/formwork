package com.example.service.impl;

import com.example.service.AuthService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService {
    @Value("${spring.mail.username}")
    String from;
    @Resource
    MailSender mailSender;
    @Resource
    RedisTemplate redisTemplate;

    @Override
    public boolean sendValidateEmail(String email, String sessionId) {
        String key = getKey(sessionId, email);
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            Long expire = Optional.ofNullable(redisTemplate.getExpire(key, TimeUnit.SECONDS)).orElse(0L);
            if (expire > 120) {
                return false;
            }
        }
        Random random = new Random();
        int code = random.nextInt(899999) + 100000;
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("你的验证邮件");
        simpleMailMessage.setText("验证码为: " + code);
        try {
            mailSender.send(simpleMailMessage);
            redisTemplate.opsForValue().set(key, String.valueOf(code), 3, TimeUnit.MINUTES);
            return true;
        } catch (MailException e) {
            e.printStackTrace();
            return false;
        }

    }

    private String getKey(String sessionId, String email) {
        return "email:" + sessionId + ":" + email;
    }
}
