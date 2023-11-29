package com.example.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDetailVo {
    Integer id;
    String title;
    String content;
    Integer type;
    Date time;
    User user;
    Interact interact;
    @Data
    @AllArgsConstructor
    public static class Interact{
        Boolean like;
        Boolean collect;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User{
        Integer uid;
        String username;
        String avatar;
        String desc;
        String qq;
        String wx;
        String phone;
        String email;
    }
}
