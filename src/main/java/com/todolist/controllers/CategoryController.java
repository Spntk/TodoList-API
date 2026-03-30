package com.todolist.controllers;

import com.todolist.dtos.CategoryDto;
import com.todolist.dtos.CategoryRequestDto;
import com.todolist.service.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategoryById(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(categoryService.getCategoriesByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(HttpServletRequest request,
            @RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(categoryService.createCategory(userId, categoryRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(HttpServletRequest request, @PathVariable Long id,
            @RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(categoryService.updateCategory(id, userId, categoryRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        categoryService.deleteCategory(id, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/bulk-delete")
    public ResponseEntity<Void> bulkDeleteCategory(HttpServletRequest request, @RequestBody List<Long> ids) {
        Long userId = (Long) request.getAttribute("userId");
        categoryService.bulkDelete(ids, userId);
        return ResponseEntity.noContent().build();
    }

}
