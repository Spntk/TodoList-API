package com.todolist.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todolist.models.CategoryModel;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {
    List<CategoryModel> findByUserId(Long userId);

    Optional<CategoryModel> findByIdAndUserId(Long id, Long userId);
}
