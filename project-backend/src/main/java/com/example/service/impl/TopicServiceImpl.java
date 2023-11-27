package com.example.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.example.entity.Topic;
import com.example.entity.TopicType;
import com.example.entity.vo.TopicVo;
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
}
