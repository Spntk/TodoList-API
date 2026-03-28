package com.todolist.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todolist.models.CategoryModel;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {
    List<CategoryModel> findByUserId(Long userId);
}
