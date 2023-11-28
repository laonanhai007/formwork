package com.example.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.example.entity.AccountInfo;
import com.example.entity.Topic;
import com.example.entity.TopicType;
import com.example.entity.vo.TopicDetailVo;
import com.example.entity.vo.TopicPreviewVo;
import com.example.entity.vo.TopicVo;
import com.example.mapper.AccountMapper;
import com.example.mapper.TopicMapper;
import com.example.service.TopicService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    TopicMapper topicMapper;
    @Autowired
    AccountMapper accountMapper;

    @Override
    public List<TopicType> listTypes() {
        return topicMapper.findAll();
    }

    @Override
    public String createTopic(Integer id, TopicVo topicVo) {
        Set<Integer> types = this.listTypes().stream().map(TopicType::getId).collect(Collectors.toSet());
        if (!types.contains(topicVo.getType())) {
            return "非法帖子类型";
        }
        if (!textLimitCheck(topicVo.getContent())) {
            return "帖子字数超过限制";
        }
        Topic topic = new Topic();
        BeanUtils.copyProperties(topicVo, topic);
        topic.setContent(topicVo.getContent().toJSONString());
        topic.setUid(id);
        topic.setTime(new Date());
        try {
            topicMapper.saveTopic(topic);
        } catch (Exception e) {
            return "服务器内部发生错误,请联系管理员";
        }
        return null;
    }

    @Override
    public List<TopicPreviewVo> topicList(int page, int type) {
        List<TopicPreviewVo> topicPreviewVos;
        if (type == 0) {
            topicPreviewVos = topicMapper.selectTopics(page);
        } else {
            topicPreviewVos = topicMapper.selectTopicsByType(page, type);
        }
        topicPreviewVos.forEach(topicPreviewVo -> {
            topicPreviewVo.setContent("Hello World");
        });
        return topicPreviewVos;
    }

    @Override
    public TopicDetailVo getTopicDetailVo(Integer id) {
        Topic topic = topicMapper.selectTopicById(id);
        Integer uid = topic.getUid();
        TopicDetailVo topicDetailVo = new TopicDetailVo();
        BeanUtils.copyProperties(topic, topicDetailVo);
        AccountInfo accountInfo = accountMapper.findAccountInfoById(uid);
        TopicDetailVo.User user = new TopicDetailVo.User();
        BeanUtils.copyProperties(accountInfo, user);
        topicDetailVo.setUser(user);
        return topicDetailVo;
    }

    private boolean textLimitCheck(JSONObject object) {
        if (object == null) return false;
        long length = 0;
        for (Object op : object.getJSONArray("ops")) {
            length += JSONObject.from(op).getString("insert").length();
            if (length > 10000) {
                return false;
            }
        }
        return true;
    }

    private StringBuilder parse(String content) {
        StringBuilder parse = null;
        JSONObject jsonObject = JSONObject.parseObject(content);
        for (Object op : jsonObject.getJSONArray("ops")) {
            parse.append(JSONObject.from(op).getString("insert"));
        }
        return parse;
    }
}
