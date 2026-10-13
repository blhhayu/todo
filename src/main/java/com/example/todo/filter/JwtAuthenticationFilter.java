package com.example.todo.filter;

import com.example.todo.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        // 有 Token 才尝试解析，没 Token 直接放行，交给 Security 决定
        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7);
            if (jwtUtil.validateToken(jwt)) {
                Long userId = jwtUtil.getUserIdFromToken(jwt);
                // 新增：校验是否在黑名单
                String jti = jwtUtil.getJtiFromToken(jwt);

                Boolean isBlacklisted = false;

                try {
                    Boolean result = stringRedisTemplate.hasKey("blacklist:jti:" + jti);
                    isBlacklisted = Boolean.TRUE.equals(result);
                } catch (Exception e) {
                    log.warn("Redis不可用，跳过黑名单检查",e);
                }

                if (isBlacklisted) {
                    // 已在黑名单，不设置 authentication，交给 Security 返回 401
                    filterChain.doFilter(request, response);
                    return;
                }

                // 把用户身份放入 SecurityContext，标记为已认证
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}