package com.example.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.example.entity.AccountInfo;
import com.example.entity.Topic;
import com.example.entity.TopicType;
import com.example.entity.dto.Interact;
import com.example.entity.vo.TopicDetailVo;
import com.example.entity.vo.TopicPreviewVo;
import com.example.entity.vo.TopicVo;
import com.example.mapper.AccountMapper;
import com.example.mapper.TopicMapper;
import com.example.service.TopicService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    TopicMapper topicMapper;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    StringRedisTemplate redisTemplate;

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
            topicPreviewVo.setLike(topicMapper.interactCount(topicPreviewVo.getId(), "like"));
            topicPreviewVo.setLike(topicMapper.interactCount(topicPreviewVo.getId(), "collect"));
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
        TopicDetailVo.Interact interact = new TopicDetailVo.Interact(
                hasInteract(id, uid, "like"),
                hasInteract(id, uid, "collect")
        );
        AccountInfo accountInfo = accountMapper.findAccountInfoById(uid);
        TopicDetailVo.User user = new TopicDetailVo.User();
        BeanUtils.copyProperties(accountInfo, user);
        topicDetailVo.setUser(user);
        topicDetailVo.setInteract(interact);
        return topicDetailVo;
    }

    @Override
    public void interact(Interact interact, boolean state) {
        String type = interact.getType();
        synchronized (type.intern()) {
            redisTemplate.opsForHash().put(type, interact.toKey(), Boolean.toString(state));
            this.saveInteractSchedule(type);
        }
    }

    @Override
    public List<TopicPreviewVo> listTopicCollects(Integer uid) {
        List<TopicPreviewVo> topicPreviewVos = topicMapper.selectCollectedTopic(uid);
        topicPreviewVos.forEach(topicPreviewVo -> {
            topicPreviewVo.setLike(topicMapper.interactCount(topicPreviewVo.getId(), "like"));
            topicPreviewVo.setLike(topicMapper.interactCount(topicPreviewVo.getId(), "collect"));
            topicPreviewVo.setContent("帖子预览内容,暂时未处理");
        });
        return topicPreviewVos;
    }

    @Override
    public void cancelCollect(Integer tid, Integer id) {
        topicMapper.deleteInteract(tid, id, "collect");
    }

    private final Map<String, Boolean> state = new HashMap<>();
    ScheduledExecutorService service = Executors.newScheduledThreadPool(2);

    private void saveInteractSchedule(String type) {
        if (!state.getOrDefault(type, false)) {
            state.put(type, true);
            service.schedule(() -> {
                this.saveInteract(type);
                state.put(type, false);
            }, 3, TimeUnit.SECONDS);
        }
    }

    private void saveInteract(String type) {
        synchronized (type.intern()) {
            List<Interact> check = new ArrayList<>();
            List<Interact> uncheck = new ArrayList<>();
            redisTemplate.opsForHash().entries(type).forEach((k, v) -> {
                if (Boolean.parseBoolean(v.toString())) {
                    check.add(Interact.parse(k.toString(), type));
                } else {
                    uncheck.add(Interact.parse(k.toString(), type));
                }
            });
            if (!check.isEmpty()) {
                check.forEach(c -> {
                    topicMapper.addInteract(c.getTid(), c.getUid(), c.getDate(), type);
                });
            }
            if (!uncheck.isEmpty()) {
                uncheck.forEach(c -> {
                    topicMapper.deleteInteract(c.getTid(), c.getUid(), type);
                });
            }
            redisTemplate.delete(type);
        }
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

    private boolean hasInteract(Integer tid, Integer uid, String type) {
        String key = tid + ":" + uid;
        if (redisTemplate.opsForHash().hasKey(type, key)) {
            return Boolean.parseBoolean(redisTemplate.opsForHash().entries(type).get(key).toString());
        }
        return topicMapper.userInteractCount(tid, uid, type) > 0;
    }
}
