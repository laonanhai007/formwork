package com.example.service;

import com.example.entity.TopicType;
import com.example.entity.dto.Interact;
import com.example.entity.vo.TopicDetailVo;
import com.example.entity.vo.TopicPreviewVo;
import com.example.entity.vo.TopicVo;

import java.util.List;

public interface TopicService {
    List<TopicType> listTypes();

    String createTopic(Integer id, TopicVo topicVo);

    List<TopicPreviewVo> topicList(int page,int type);

    TopicDetailVo getTopicDetailVo(Integer id);

    void interact(Interact interact,boolean state);

    List<TopicPreviewVo> listTopicCollects(Integer uid);

    void cancelCollect(Integer tid, Integer id);
}
