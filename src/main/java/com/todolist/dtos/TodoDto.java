package com.todolist.dtos;

public class TodoDto {
    private Long id;
    private String title;
    private String description;
    private CategoryDto category;
    private String priority;
    private String status;
    private String dueDate;
    private String createdAt;

    public TodoDto(Long id, String title, String description, CategoryDto category, String priority, String status,
            String dueDate, String createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.priority = priority;
        this.status = status;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public Long getId() {
        return id;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }
}
