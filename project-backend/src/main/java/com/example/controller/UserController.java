package com.example.controller;

import com.example.entity.AccountInfo;
import com.example.entity.RestBean;
import com.example.entity.auth.Account;
import com.example.entity.user.AccountDto;
import com.example.mapper.AccountMapper;
import com.example.service.UserService;
import jakarta.annotation.Resource;
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

    @Resource
    UserService userService;

    @PostMapping("/save-info")
    public RestBean saveInfo(@RequestBody AccountInfo info,@SessionAttribute("account") AccountDto accountDto) {
        if (info.getUid() == null) return RestBean.failure(402, "请填写完整信息");
        if (info.getUid().equals(accountDto.getId())){
            userService.saveUserInfo(info);
            return RestBean.success("保存成功");
        }
        else return RestBean.failure(402,"非法表单操作!");

    }
}
