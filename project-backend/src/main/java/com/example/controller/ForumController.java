package com.example.controller;

import com.example.entity.RestBean;
import com.example.entity.TopicType;
import com.example.service.TopicService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/forum")
public class ForumController {
    @Resource
    TopicService topicService;
    @PostMapping("/types")
    public RestBean<List<TopicType>> getTypes(){
           List<TopicType> list =  topicService.listTypes();
           return RestBean.success(list );
    }
}
