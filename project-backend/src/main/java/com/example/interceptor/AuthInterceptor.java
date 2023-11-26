package com.example.interceptor;

import com.example.entity.user.AccountDto;
import com.example.mapper.AccountMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Resource
    AccountMapper accountMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("account") == null){
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            User user = (User) authentication.getPrincipal();
            String username = user.getUsername();
            AccountDto accountDto = accountMapper.findAccountUserByUsernameOrEmail(username);
            request.getSession().setAttribute("account",accountDto);
        }
        return true;
    }
}
