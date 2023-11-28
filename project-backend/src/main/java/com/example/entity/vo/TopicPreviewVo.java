package com.example.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicPreviewVo {
    Integer id;
    String title;
    String content;
    Integer uid;
    Integer type;
    String username;
    String avatar;
}
