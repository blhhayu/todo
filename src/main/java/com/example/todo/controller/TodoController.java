package com.example.todo.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.todo.common.Result;
import com.example.todo.dto.UpdateTodoDTO;
import com.example.todo.entity.Todo;
import com.example.todo.service.TodoService;
import com.example.todo.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
@Validated
@Tag(name = "待办事项管理", description = "待办事项的增删改查接口")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    @Operation(summary = "获取所有待办事项", description = "获取当前用户的所有待办事项")
    public Result<List<Todo>> findAll(){
        Long userId = SecurityUtil.getCurrentUserId();
        return Result.success(todoService.findAll(userId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取待办事项", description = "根据ID获取单个待办事项详情")
    public Result<Todo> findById(@PathVariable Long id){
        Long userId = SecurityUtil.getCurrentUserId();
        return Result.success(todoService.findById(id,userId));
    }

    @PostMapping
    @Operation(summary = "新增待办事项", description = "创建一个新的待办事项")
    public Result<Todo> add(@Valid @RequestBody Todo todo){
        Long userId = SecurityUtil.getCurrentUserId();
        todo.setUserId(userId);
        return Result.success(todoService.add(todo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改待办事项", description = "根据ID修改待办事项")
    public Result<Todo> update(@PathVariable Long id, @RequestBody UpdateTodoDTO dto){
        Long userId = SecurityUtil.getCurrentUserId();
        return Result.success(todoService.update(id, dto,userId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除待办事项", description = "根据ID删除待办事项")
    public Result delete(@PathVariable Long id){
        Long userId = SecurityUtil.getCurrentUserId();
        todoService.delete(id,userId);
        return Result.success();
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询待办事项", description = "支持分页和按完成状态筛选")
    public Result<Page<Todo>> findPage(
            @RequestParam(defaultValue = "1")
            @Min(value = 1, message = "页码必须大于 0")
            Integer pageNum,
            @RequestParam(defaultValue = "10")
            @Min(value = 1, message = "每页条数必须大于 0")
            @Max(value = 100, message = "每页条数不能超过 100")
            Integer pageSize,
            @RequestParam(required = false) Boolean completed
    ){
        Long userId = SecurityUtil.getCurrentUserId();
        return Result.success(todoService.findPage(pageNum, pageSize, completed,userId));
    }
}