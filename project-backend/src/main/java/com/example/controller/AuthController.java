package com.example.controller;

import com.example.entity.RestBean;
import com.example.service.AuthService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final String EMAIL_REGEXP = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    @Resource
    private AuthService authService;

    @PostMapping("/valid-email")
    public RestBean<String> sendValidateEmail(@Pattern(regexp = EMAIL_REGEXP)
                                              @RequestParam("email") String email,
                                              HttpSession session) {
        String message = authService.sendValidateEmail(email, session.getId());
        if (message == null) {
            return RestBean.success("已发送邮件,注意查收");
        } else {
            return RestBean.failure(401, message);
        }
    }

    @PostMapping("/register")
//    TODO 字段校验
    public RestBean register(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("email") String email,
                             @RequestParam("code") String code,
                             HttpSession session) {

        String message = authService.validateAndRegister(username, password, email, code, session.getId());
        if (message == null) {
            return RestBean.success("注册成功");
        } else {
            return RestBean.failure(401, message);
        }
    }
}
