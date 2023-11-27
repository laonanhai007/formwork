package com.example.mapper;

import com.example.entity.TopicType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TopicMapper {

    @Select("select  * from db_topic_type")
    List<TopicType> findAll();
}
