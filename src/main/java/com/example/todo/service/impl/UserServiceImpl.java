package com.example.todo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.todo.dto.RegisterDTO;
import com.example.todo.entity.User;
import com.example.todo.exception.AuthFailedException;
import com.example.todo.exception.BadRequestException;
import com.example.todo.exception.NotFoundException;
import com.example.todo.mapper.UserMapper;
import com.example.todo.service.UserService;
import com.example.todo.util.JwtUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private static final String LOGIN_FAIL_KEY = "login:fail:";
    private static final int MAX_FAIL = 5;
    private static final long LOCK_SECONDS = 15 * 60;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 用户注册
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public User register(RegisterDTO dto) {
        // 1. 校验用户名是否已被占用
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", dto.getUsername());
        User existingUser = userMapper.selectOne(wrapper);
        if (existingUser != null) {
            throw new BadRequestException("用户名已被占用");
        }

        // 2. 加密密码
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(encodedPassword);

        // 3. 存入数据库
        userMapper.insert(user);

        return user;
    }

    /**
     * 用户登入
     *
     * @param username 用户名
     * @param password 明文密码
     * @return
     */
    @Override
    public String login(String username, String password) {

        String key = LOGIN_FAIL_KEY + username;

        // 0. 检查是否已锁定
        String failCount = stringRedisTemplate.opsForValue().get(key);
        if (failCount != null && Integer.parseInt(failCount) >= MAX_FAIL) {
            throw new AuthFailedException("账号已锁定，请 15 分钟后重试");
        }

        // 1. 查询用户
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            throw new NotFoundException("用户名不存在");
        }

        //2. 验证密码（使用 BCrypt）
        if (!passwordEncoder.matches(password, user.getPassword())) {
            Long count = stringRedisTemplate.opsForValue().increment(key);
            if (count != null && count == 1) {
                stringRedisTemplate.expire(key, LOCK_SECONDS, TimeUnit.SECONDS);
            }
            throw new AuthFailedException("密码错误");
        }

        // 3. 登录成功，清空失败计数
        stringRedisTemplate.delete(key);

        // 4. 生成 JWT Token
        return jwtUtil.generateToken(user.getId());
    }

    @Override
    public void logout(String token) {
        String jti = jwtUtil.getJtiFromToken(token);
        Date expiration = jwtUtil.getExpirationFromToken(token);
        long ttl = expiration.getTime() - System.currentTimeMillis();
        if (ttl > 0) {
            stringRedisTemplate.opsForValue().set(
                    "blacklist:jti:" + jti, "1", ttl, TimeUnit.MILLISECONDS);
        }
    }
}
