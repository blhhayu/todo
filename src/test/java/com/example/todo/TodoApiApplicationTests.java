package com.example.todo;

import com.example.todo.entity.Todo;
import com.example.todo.mapper.TodoMapper;
import com.example.todo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TodoApiApplicationTests {

    @Autowired
    private TodoMapper todoMapper;

    @Autowired
    private UserMapper userMapper;

    @Test
    void testSelectAll() {
        List<Todo> todos = todoMapper.selectList(null);
        System.out.println("查询结果数量：" + todos.size());
        todos.forEach(System.out::println);
    }

    @Test
    void testUserMapper() {
        System.out.println("查询用户表: " + userMapper.selectList(null));
    }

}
