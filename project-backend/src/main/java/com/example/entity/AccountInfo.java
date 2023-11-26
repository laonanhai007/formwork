package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfo {
    
    private Integer uid;

    private String username;
    private String sex;
    
    private String phone;
    
    private String qq;
    
    private String wx;
    
    private String blog;
    
    private String introduce;

}

