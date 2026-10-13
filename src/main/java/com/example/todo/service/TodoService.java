package com.example.todo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.todo.dto.UpdateTodoDTO;
import com.example.todo.entity.Todo;

import java.util.List;

public interface TodoService {
    /**
     * 查询所有待办事项
     * @return
     */
    List<Todo> findAll(Long userId);

    /**
     * 根据id查询单个待办事项
     * @param id
     * @return
     */
    Todo findById(Long id,Long userId);

    /**
     * 新增待办事项
     * @param todo
     */
    Todo add(Todo todo);

    /**
     * 修该待办事项
     * @param dto
     * @return
     */
    Todo update(Long id, UpdateTodoDTO dto, Long userId);

    /**
     * 删除待办事项
     */
    void delete(Long id,Long userId);

    /**
     * 分页条件查询
     * @param pageNum 页码（从1开始）
     * @param pageSize 每页条数
     * @param completed 是否完成（可选，为null时查询所有）
     */
    Page<Todo> findPage(Integer pageNum, Integer pageSize, Boolean completed, Long userId);
}
