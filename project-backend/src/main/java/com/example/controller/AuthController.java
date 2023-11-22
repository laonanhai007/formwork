package com.example.controller;

import com.example.entity.RestBean;
import com.example.service.AuthService;
import jakarta.annotation.Resource;
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
    public RestBean sendValidateEmail(@Pattern (regexp = EMAIL_REGEXP)
                                          @RequestParam("email") String email){

        if (authService.sendValidateEmail(email)) {
            return RestBean.success("已发送邮件,注意查收");
        }else {
            return RestBean.failure(401,"服务器发送邮件异常");
        }
    }
}
