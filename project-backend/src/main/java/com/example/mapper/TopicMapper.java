package com.example.mapper;

import com.example.entity.Topic;
import com.example.entity.TopicType;
import com.example.entity.vo.TopicPreviewVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface TopicMapper {

    @Select("select  * from db_topic_type")
    List<TopicType> findAll();

    @Insert("insert into db_topic(title, content, uid, type,time) VALUES (#{title},#{content},#{uid},#{type},#{time}) ")
    void saveTopic(Topic topic);

    @Select("""
            SELECT dt.*,da.username,da.avatar FROM
            db_topic dt,db_account da
            WHERE dt.uid = da.id and dt.type = #{type}
            ORDER BY dt.time
            """)
    List<TopicPreviewVo> selectTopicsByType(int start, int type);


    @Select("""
            SELECT dt.*,da.username,da.avatar FROM
            db_topic dt,db_account da
            WHERE dt.uid = da.id
            ORDER BY dt.time
            """)
    List<TopicPreviewVo> selectTopics(int start);

    @Select("select * from db_topic where id=#{id}")
    Topic selectTopicById(Integer id);

}
