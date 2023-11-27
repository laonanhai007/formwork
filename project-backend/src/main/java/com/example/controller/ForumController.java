package com.example.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.entity.RestBean;
import com.example.entity.TopicType;
import com.example.entity.auth.Account;
import com.example.entity.user.AccountDto;
import com.example.entity.vo.TopicVo;
import com.example.service.TopicService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forum")
public class ForumController {
    @Autowired
    TopicService topicService;

    @PostMapping("/types")
    public RestBean<List<TopicType>> getTypes() {
        List<TopicType> list = topicService.listTypes();
        return RestBean.success(list);
    }

    @PostMapping("/create-topic")
    public RestBean<String> createTopic(
            @RequestBody TopicVo topicVo,
            @SessionAttribute("account")AccountDto accountDto) {

        String msg = topicService.createTopic(accountDto.getId(), topicVo);
        if (msg == null) {
            return RestBean.success("发表帖子成功");
        } else {
            return RestBean.failure(405, msg);
        }
    }
}
