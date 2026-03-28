package com.todolist.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.todolist.dtos.CategoryDto;
import com.todolist.dtos.CategoryRequestDto;
import com.todolist.models.CategoryModel;
import com.todolist.models.UserModel;
import com.todolist.repositories.CategoryRepository;
import com.todolist.repositories.UserRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    private static final List<String> DEFUALT_CATEGORIES = List.of(
            "Work", "Development", "Meeting", "Personal");

    public void createDefualtCategories(UserModel userModel) {
        List<CategoryModel> defaults = DEFUALT_CATEGORIES.stream()
                .map(name -> {
                    CategoryModel categoryModel = new CategoryModel();
                    categoryModel.setCategoryName(name);
                    categoryModel.setUser(userModel);
                    return categoryModel;
                }).toList();

        categoryRepository.saveAll(defaults);
    }

    public List<CategoryDto> getCategoriesByUserId(Long userId) {
        return categoryRepository.findByUserId(userId)
                .stream()
                .map(category -> new CategoryDto(
                        category.getId(),
                        category.getCategoryName(),
                        category.getCreatedAt()))
                .toList();
    }

    public CategoryDto createCategory(Long userId, CategoryRequestDto categoryRequestDto) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setCategoryName(categoryRequestDto.getCategoryName());
        categoryModel.setUser(user);

        CategoryModel saved = categoryRepository.save(categoryModel);
        return new CategoryDto(saved.getId(), saved.getCategoryName(), saved.getCreatedAt());
    }

    public CategoryDto updateCategory(Long categoryId, Long userId, CategoryRequestDto categoryRequestDto) {
        CategoryModel categoryModel = categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        categoryModel.setCategoryName(categoryRequestDto.getCategoryName());

        CategoryModel saved = categoryRepository.save(categoryModel);
        return new CategoryDto(saved.getId(), saved.getCategoryName(), saved.getCreatedAt());
    }

    public void deleteCategory(Long categoryId, Long userId) {
        CategoryModel categoryModel = categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        categoryRepository.delete(categoryModel);
    }
}
