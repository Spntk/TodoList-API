package com.todolist.dtos;

import java.time.LocalDate;

import com.todolist.models.enums.TodoPriority;
import com.todolist.models.enums.TodoStatus;

public class TodoRequestDto {
    private String title;
    private String description;
    private Long categoryId;
    private TodoPriority priority;
    private TodoStatus status;
    private LocalDate dueDate;

    public Long getCategoryId() {
        return categoryId;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public TodoPriority getPriority() {
        return priority;
    }

    public TodoStatus getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }
}
