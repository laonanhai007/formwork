package com.example.mapper;

import com.example.entity.TopicComment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TopicCommentMapper {
    @Insert("insert into db_topic_comment(uid, tid, content, time, quote) " +
            "VALUES (#{uid},#{tid},#{content},#{time},#{quote})")
    void insertComment(TopicComment comment);


    @Select("select * from db_topic_comment where tid = #{tid}")
    List<TopicComment> selectTopicCommentsById(Integer tid);

    @Select("select * from db_topic_comment where id=#{id}")
    TopicComment selectByCommentId(Integer id);
}
