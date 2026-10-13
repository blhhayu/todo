package com.example.todo.controller;

import com.example.todo.common.Result;
import com.example.todo.dto.LoginDTO;
import com.example.todo.dto.RegisterDTO;
import com.example.todo.entity.User;
import com.example.todo.service.UserService;
import com.example.todo.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/users")
@Tag(name = "用户管理", description = "用户注册和登录接口")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "注册新用户，用户名不能重复")
    public Result<UserVO> register(@Valid @RequestBody RegisterDTO dto) {
            User registeredUser = userService.register(dto);
            return Result.success(UserVO.from(registeredUser));
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "登录成功后返回JWT Token")
    public Result<String> login(@Valid @RequestBody LoginDTO loginRequest){
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
            String token = userService.login(username, password);
            return Result.success(token);
    }

    @PostMapping("/logout")
    @Operation(summary = "退出登录", description = "把当前 Token 的 jti 加入 Redis 黑名单")
    public Result<Void> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        userService.logout(token);
        return Result.success();
    }
}