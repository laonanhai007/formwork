package com.example.mapper;

import com.example.entity.Topic;
import com.example.entity.TopicType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface TopicMapper {

    @Select("select  * from db_topic_type")
    List<TopicType> findAll();

    @Insert("insert into db_topic(titile, content, uid, type,time) VALUES (#{title},#{content},#{uid},#{type},#{time}) ")
    void saveTopic(Topic topic);
}
