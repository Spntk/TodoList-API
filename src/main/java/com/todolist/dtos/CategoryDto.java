package com.todolist.dtos;

import java.time.LocalDateTime;

public class CategoryDto {
    private Long id;
    private String categoryName;
    private LocalDateTime createdAt;

    public CategoryDto(Long id, String categoryName, LocalDateTime createdAt) {
        this.id = id;
        this.categoryName = categoryName;
        this.createdAt = createdAt;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getId() {
        return id;
    }
}
