package com.example.todo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateTodoDTO {
    @NotBlank(message = "事项标题不能为空")
    private String title;

    private String description;

    private Boolean completed;
}
