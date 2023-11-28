package com.example.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.entity.RestBean;
import com.example.entity.TopicType;
import com.example.entity.auth.Account;
import com.example.entity.user.AccountDto;
import com.example.entity.vo.TopicDetailVo;
import com.example.entity.vo.TopicPreviewVo;
import com.example.entity.vo.TopicVo;
import com.example.service.TopicService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forum")
public class ForumController {
    @Autowired
    TopicService topicService;

    @GetMapping("/types")
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

    @PostMapping("/list-topic")
    public RestBean<List<TopicPreviewVo>> listTopic(@RequestParam int page,@RequestParam int type){
        List<TopicPreviewVo> topicPreviewVos = topicService.topicList(page, type);
        return RestBean.success(topicPreviewVos);
    }

    @GetMapping("/topic")
    public RestBean<TopicDetailVo> topic(@RequestParam("tid") Integer tid){
        return RestBean.success(topicService.getTopicDetailVo(tid));
    }

    @GetMapping("/interact")
    public RestBean<Void> interact(@RequestParam("tid") Integer tid,
                                   @RequestParam @Pattern(regexp = "(like|collect)") String type,
                                   @RequestParam boolean state,
                                   @SessionAttribute("account") AccountDto account){

        return RestBean.success();
    }
}
