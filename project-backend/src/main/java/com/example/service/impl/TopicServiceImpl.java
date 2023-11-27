package com.example.service.impl;

import com.example.entity.TopicType;
import com.example.mapper.TopicMapper;
import com.example.service.TopicService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    @Resource
    TopicMapper topicMapper;
    @Override
    public List<TopicType> listTypes() {
        return topicMapper.findAll();
    }
}
