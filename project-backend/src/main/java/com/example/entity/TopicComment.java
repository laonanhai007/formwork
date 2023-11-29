package com.example.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (DbTopicComment)表实体类
 *
 * @author makejava
 * @since 2023-11-29 19:39:30
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicComment {
    
    private Integer id;
    
    private Integer uid;
    
    private Integer tid;
    
    private String content;
    
    private Date time;
    
    private Integer quote;

}

