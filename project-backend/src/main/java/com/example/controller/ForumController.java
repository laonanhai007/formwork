package com.example.controller;

import com.example.entity.RestBean;
import com.example.entity.TopicType;
import com.example.entity.dto.Interact;
import com.example.entity.user.AccountDto;
import com.example.entity.vo.*;
import com.example.service.TopicService;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
            @SessionAttribute("account") AccountDto accountDto) {

        String msg = topicService.createTopic(accountDto.getId(), topicVo);
        if (msg == null) {
            return RestBean.success("发表帖子成功");
        } else {
            return RestBean.failure(405, msg);
        }
    }

    @PostMapping("/list-topic")
    public RestBean<List<TopicPreviewVo>> listTopic(@RequestParam int page, @RequestParam int type) {
        List<TopicPreviewVo> topicPreviewVos = topicService.topicList(page, type);
        return RestBean.success(topicPreviewVos);
    }

    @GetMapping("/topic")
    public RestBean<TopicDetailVo> topic(@RequestParam("tid") Integer tid,
                                         @SessionAttribute("account") AccountDto account) {
        return RestBean.success(topicService.getTopicDetailVo(tid, account.getId()));
    }

    @GetMapping("/interact")
    public RestBean<Void> interact(@RequestParam("tid") Integer tid,
                                   @RequestParam @Pattern(regexp = "(like|collect)") String type,
                                   @RequestParam boolean state,
                                   @SessionAttribute("account") AccountDto account) {
        topicService.interact(new Interact(tid, account.getId(), new Date(), type), state);
        return RestBean.success();
    }

    @GetMapping("/cancel-collect")
    public RestBean<Void> cancelCollect(@RequestParam("tid") Integer tid,
                                        @SessionAttribute("account") AccountDto account) {
        topicService.cancelCollect(tid, account.getId());
        return RestBean.success();
    }

    @GetMapping("/collect")
    public RestBean<List<TopicPreviewVo>> collectTopics(@SessionAttribute("account") AccountDto accountDto) {
        List<TopicPreviewVo> topicPreviewVos = topicService.listTopicCollects(accountDto.getId());
        return RestBean.success(topicPreviewVos);
    }

    @PostMapping("/update-topic")
    public RestBean<String> updateTopic(@RequestBody TopicUpdateVo topicUpdateVo, @SessionAttribute("account") AccountDto accountDto) {
        String s = topicService.updateTopic(accountDto.getId(), topicUpdateVo);
        if (s == null)
            return RestBean.success("更新帖子成功");
        else return RestBean.failure(402,"更新文章失败");
    }

    @PostMapping("/add-comment")
    public RestBean<Void> addComment(@RequestBody AddCommentVo addCommentVo,@SessionAttribute("account") AccountDto accountDto){
        topicService.createComment(addCommentVo,accountDto.getId());
        return RestBean.success();
    }
}
