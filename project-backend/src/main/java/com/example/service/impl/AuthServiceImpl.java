package com.example.service.impl;

import com.example.entity.auth.Account;
import com.example.mapper.AccountMapper;
import com.example.service.AuthService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
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
    @Resource
    AccountMapper accountMapper;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String sendValidateEmail(String email, String sessionId, boolean hasAccount) {
        Account account = accountMapper.findAccountByEmail(email);
        if (account == null && hasAccount){
            return "不存在此邮箱";
        }
        if (account != null && !hasAccount){
            return "该邮箱已存在,请更换邮箱";
        }
        String key = getKey(sessionId, email)+":"+hasAccount;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            Long expire = Optional.ofNullable(redisTemplate.getExpire(key, TimeUnit.SECONDS)).orElse(0L);
            if (expire > 120) {
                return "请稍后再试";
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
            return null;
        } catch (MailException e) {
            e.printStackTrace();
            return "邮件发送失败";
        }

    }


    @Override
    public String validateAndRegister(String username, String password, String email, String code, String sessionId) {
        String key = getKey(sessionId, email)+":false";
        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            return "邮箱地址和获取验证的邮箱不同";
        }
        if (redisTemplate.getExpire(key, TimeUnit.SECONDS) < 0) {
            return "验证码超时,请重新获取验证码";
        }
        if (!Objects.equals(redisTemplate.opsForValue().get(key), code)) {
            return "验证码错误,请仔细检查验证码";
        }
        if (accountMapper.findAccountByUsernameOrEmail(username) != null) {
            return "用户名已存在,请更换用户名";
        }
        password = encoder.encode(password);
        if (accountMapper.createAccount(email, username, password) < 0) {
            return "创建用户失败,请联系管理员";
        }
        redisTemplate.delete(key);
        return null;
    }

//    重置密码的验证
    @Override
    public String validateAndReset(String email, String code, String sessionId) {
        String key = getKey(sessionId, email)+":true";
        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            return "邮箱地址和获取验证的邮箱不同";
        }
        if (redisTemplate.getExpire(key, TimeUnit.SECONDS) < 0) {
            return "验证码超时,请重新获取验证码";
        }
        if (!Objects.equals(redisTemplate.opsForValue().get(key), code)) {
            return "验证码错误,请仔细检查验证码";
        }
        redisTemplate.delete(key);
        return null;
    }

    @Override
    public String resetPassword(String password, HttpSession session) {
        String email = (String) session.getAttribute("reset-email");
        if (email == null){
            return "请先完成邮箱验证";
        }
        int i =  accountMapper.updatePasswordByEmail(encoder.encode(password),email);
        if (i< 1) {
            return "重置密码异常,请联系管理员";
        }
        session.removeAttribute("reset-email");
        return null;
    }

    private String getKey(String sessionId, String email) {
        return "email:" + sessionId + ":" + email;
    }
}
