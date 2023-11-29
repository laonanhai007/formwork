package com.example.entity.vo;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicUpdateVo {
    Integer id;
    Integer type;
    String title;
    JSONObject content;
}
