package com.example.todo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.todo.dto.UpdateTodoDTO;
import com.example.todo.entity.Todo;
import com.example.todo.exception.ForbiddenException;
import com.example.todo.exception.NotFoundException;
import com.example.todo.mapper.TodoMapper;
import com.example.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoMapper todoMapper;

    @Override
    public List<Todo> findAll(Long userId) {
        // 查询当前用户的所有待办
        return todoMapper.selectList(new QueryWrapper<Todo>().eq("user_id", userId));
    }

    @Override
    public Todo findById(Long id,Long userId) {
        Todo todo = todoMapper.selectById(id);
        if (todo == null) {
            throw new NotFoundException("待办不存在");
        }
        if (!todo.getUserId().equals(userId)) {
            throw new ForbiddenException("无权限操作此待办");
        }
        return todoMapper.selectById(id);
    }

    /**
     * 新增待办事项
     * @param todo
     * @return
     */
    @Override
    @Transactional
    public Todo add(Todo todo) {
        todoMapper.insert(todo);
        return todo;
    }

    /**
     * 修该待办事项
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public Todo update(Long id, UpdateTodoDTO dto, Long userId) {
        // 1. 先根据 ID 查询待办是否存在
        Todo existingTodo = todoMapper.selectById(id);
        if (existingTodo == null) {
            throw new NotFoundException("待办不存在");
        }
        // 2. 校验是否属于当前用户
        if (!existingTodo.getUserId().equals(userId)) {
            throw new ForbiddenException("无权限操作此待办");
        }
        existingTodo.setId(id);
        existingTodo.setTitle(dto.getTitle());
        existingTodo.setDescription(dto.getDescription());
        existingTodo.setCompleted(dto.getCompleted());
        // 3. 执行更新（只更新前端传入的非空字段）
        todoMapper.updateById(existingTodo);
        return existingTodo;
    }

    /**
     * 删除待办事项
     */
    @Override
    @Transactional
    public void delete(Long id,Long userId) {
        Todo todo = todoMapper.selectById(id);
        if (todo == null) {
            throw new NotFoundException("待办不存在");
        }
        if (!todo.getUserId().equals(userId)) {
            throw new ForbiddenException("无权限操作此待办");
        }
        todoMapper.deleteById(id);
    }

    /**
     * 分页查询
     * @param pageNum 页码（从1开始）
     * @param pageSize 每页条数
     * @param completed 是否完成（可选，为null时查询所有）
     * @return
     */
    @Override
    public Page<Todo> findPage(Integer pageNum, Integer pageSize, Boolean completed, Long userId) {
        Page<Todo> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Todo> wrapper = new QueryWrapper<>();
        // 关键：分页查询也要加用户隔离条件
        wrapper.eq("user_id", userId);
        if (completed != null){
            wrapper.eq("completed",completed);
        }
        // 按创建时间倒序（最新优先）
        wrapper.orderByDesc("create_time");
        return todoMapper.selectPage(page,wrapper);
    }
}
