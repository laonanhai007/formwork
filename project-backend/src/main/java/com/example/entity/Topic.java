package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    Integer id;
    String title;
    String content;
    Integer uid;
    Integer type;
    Date time;
}
