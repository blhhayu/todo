package com.example.todo.service;

import com.example.todo.dto.RegisterDTO;
import com.example.todo.entity.User;

public interface UserService {
    /**
     * 用户注册
     * @param dto 用户信息（包含明文密码）
     * @return 注册成功后的用户（密码已置空，保证安全）
     */
    User register(RegisterDTO dto);

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 明文密码
     * @return JWT Token
     * @throws RuntimeException 用户名或密码错误
     */
    String login(String username, String password);

    void logout(String token);
}
