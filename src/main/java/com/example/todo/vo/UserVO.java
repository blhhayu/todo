package com.example.todo.vo;

import com.example.todo.entity.User;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserVO {
    private Long id;
    private String username;
    private LocalDateTime createTime;

    public static UserVO from(User user) {
        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setCreateTime(user.getCreateTime());
        return vo;
    }
}