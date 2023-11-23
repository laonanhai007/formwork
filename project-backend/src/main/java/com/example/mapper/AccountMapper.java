package com.example.mapper;

import com.example.entity.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountMapper {

    @Select(" select * from db_account where username = #{text} or email = #{text}")
    Account findAccountByUsernameOrEmail(String text);

    @Select("select * from db_account where email = #{email}")
    Account findAccountByEmail(String email);

    @Insert("insert into db_account(email, username, password) VALUES (#{email},#{username},#{password})")
    int createAccount(@Param("email") String email, @Param("username")String username, @Param("password")String password);
}
