package com.example.controller;

import com.example.entity.RestBean;
import com.example.entity.auth.Account;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/image")
@RestController
public class ImageController {


    @PostMapping("/cache")
    public RestBean<String> uploadImage(@RequestParam("file") MultipartFile file
            , @SessionAttribute("account") Account account) {
        if (file.getSize() > 1024 * 1024 * 5)
            return RestBean.failure(381, "图片大小不能超过5m");
        return RestBean.success();
    }

    @PostMapping("/test")
    public RestBean test(){
        return RestBean.success("ok");
    }

    @PostMapping("/avatar")
    public RestBean uploadAvatar(@RequestParam("file") MultipartFile file, @SessionAttribute("account") Account account) {
        if (file.getSize() > 1024 * 1024)
            return RestBean.failure(400, "头像图片大小不能大于1m");
        return RestBean.success();
    }
}
