package com.example.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private Integer id;
    private String email;
    private String username;
    private String password;
    private String avatar;
}
