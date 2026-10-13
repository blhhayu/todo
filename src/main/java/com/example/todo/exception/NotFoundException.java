package com.example.todo.exception;

// 资源不存在异常（返回404）
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}