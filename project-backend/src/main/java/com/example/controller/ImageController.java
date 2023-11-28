package com.example.controller;

import com.example.entity.RestBean;
import com.example.entity.auth.Account;
import com.example.entity.user.AccountDto;
import com.example.entity.vo.TopicVo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/image")
@RestController
public class ImageController {


    @PostMapping("/cache")
    public RestBean<String> uploadImage(@RequestParam("file") MultipartFile file
            , @SessionAttribute("account") AccountDto account) {
        if (file.getSize() > 1024 * 1024 * 5)
            return RestBean.failure(381, "图片大小不能超过5m");
        return RestBean.success();
    }

    @GetMapping("/test")
    public RestBean test(@RequestBody AccountDto accountDto) {
        System.out.println(accountDto);
        return RestBean.success("ok");
    }

    @PostMapping("/avatar")
    public RestBean uploadAvatar(@RequestParam("file") MultipartFile file, @SessionAttribute("account") AccountDto account) {
        if (file.getSize() > 1024 * 1024)
            return RestBean.failure(400, "头像图片大小不能大于1m");
        return RestBean.success();
    }


}
