package com.example.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class CommentVo {
    int id;
    String content;
    Date time;
    String quote;
    User user;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User {
        Integer id;
        String username;
        String avatar;
        String qq;
        String wx;
        String phone;
        String email;
    }
}
