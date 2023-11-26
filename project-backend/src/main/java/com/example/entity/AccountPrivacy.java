package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (DbAccountPrivacy)表实体类
 *
 * @author makejava
 * @since 2023-11-26 14:00:41
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountPrivacy {
    
    private Integer uid;
    
    private Integer email;
    
    private Integer sex;
    
    private Integer phone;
    
    private Integer qq;
    
    private Integer wx;
    
    private Integer blog;

}

