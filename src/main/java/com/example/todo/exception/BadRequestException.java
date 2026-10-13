package com.example.todo.exception;

// 参数错误异常（返回400）
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}