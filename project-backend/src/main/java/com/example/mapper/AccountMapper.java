package com.example.mapper;

import com.example.entity.AccountInfo;
import com.example.entity.auth.Account;
import com.example.entity.user.AccountDto;
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

    @Select("select id,username,email from db_account where username = #{text} or email = #{text}")
    AccountDto findAccountUserByUsernameOrEmail(String text);

    @Insert("""
            insert into db_account_info(uid, sex,phone, qq, wx, blog, introduce) 
            VALUES (#{uid},#{sex},#{phone},#{qq},#{wx},#{blog},#{introduce})
            on duplicate key update 
            uid=#{uid}, sex=#{sex}, phone=#{phone}, qq=#{qq}, wx=#{wx}, blog=#{blog}, introduce=#{introduce}
                            """)
    void saveInfo(AccountInfo info);

    @Select("select * from db_account where id=#{id}")
    Account findAccountById(Integer id);
}
