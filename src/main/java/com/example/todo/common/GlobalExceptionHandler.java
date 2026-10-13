package com.example.todo.common;

import com.example.todo.exception.AuthFailedException;
import com.example.todo.exception.BadRequestException;
import com.example.todo.exception.ForbiddenException;
import com.example.todo.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

//    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 处理所有未捕获的异常
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("系统异常", e);
        // 实际项目中应记录日志，这里简化返回错误信息
        return Result.error(500, "服务器繁忙，请稍后重试");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result<Void> handleDuplicateKey(DuplicateKeyException e) {
        return Result.error(409, "用户名已被占用");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValidation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return Result.error(400, message);
    }

    @ExceptionHandler(NotFoundException.class)
    public Result<Void> handleNotFound(NotFoundException e) {
        return Result.error(404, e.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public Result<Void> handleForbidden(ForbiddenException e) {
        return Result.error(403, e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public Result<Void> handleBadRequest(BadRequestException e) {
        return Result.error(400, e.getMessage());
    }

    @ExceptionHandler(AuthFailedException.class)
    public Result<Void> handleBadRequest(AuthFailedException e) {
        return Result.error(401, e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Void> handleConstraintViolation(ConstraintViolationException e) {
        String message = e.getConstraintViolations().iterator().next().getMessage();
        return Result.error(400, message);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<Void> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        return Result.error(400, "参数 " + e.getName() + " 格式错误");
    }
}