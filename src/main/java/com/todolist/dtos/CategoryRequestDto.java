package com.todolist.dtos;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequestDto {

    @NotBlank(message = "Category name is required")
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
