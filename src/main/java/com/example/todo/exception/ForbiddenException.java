package com.example.todo.exception;

// 无权限异常（返回403）
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}