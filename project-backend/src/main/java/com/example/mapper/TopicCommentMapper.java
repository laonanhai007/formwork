package com.example.mapper;

import com.example.entity.TopicComment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TopicCommentMapper {
    @Insert("insert into db_topic_comment(uid, tid, content, time, quote) " +
            "VALUES (#{uid},#{tid},#{content},#{time},#{quote})")
    void insertComment(TopicComment comment);

}
