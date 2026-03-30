package com.todolist.dtos;

import com.todolist.models.enums.TodoPriority;
import com.todolist.models.enums.TodoStatus;

public class TrashtodoDto {
    private Long id;
    private String title;
    private String description;
    private TodoPriority priority;
    private TodoStatus status;
    private String dueDate;
    private String deletedAt;

    public TrashtodoDto(Long id, String title, String description, TodoPriority priority, TodoStatus status,
            String dueDate, String deletedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.dueDate = dueDate;
        this.deletedAt = deletedAt;
    }

    public Long getId() {
        return id;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
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
