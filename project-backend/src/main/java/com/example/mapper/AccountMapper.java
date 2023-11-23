package com.example.mapper;

import com.example.entity.Account;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AccountMapper {

    @Select(" select * from db_account where username = #{text} or email = #{text}")
    Account findAccountByUsernameOrEmail(String text);

    @Select("select * from db_account where email = #{email}")
    Account findAccountByEmail(String email);

    @Insert("insert into db_account(email, username, password) VALUES (#{email},#{username},#{password})")
    int createAccount(@Param("email") String email, @Param("username") String username, @Param("password") String password);

    @Update("update  db_account set password = #{password} where email = #{email}")
    int updatePasswordByEmail(@Param("password") String password, @Param("email") String email);
}
