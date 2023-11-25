package com.example.controller;

import com.example.entity.RestBean;
import com.example.entity.user.AccountDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @PostMapping("/me")
    public RestBean me(@SessionAttribute("account") AccountDto accountDto, HttpSession session) {
        if (accountDto == null) {
            return RestBean.failure(399, "未登录");
        }
        session.removeAttribute("account");
        return RestBean.success(accountDto);
    }
}
