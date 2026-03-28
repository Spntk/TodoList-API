package com.todolist.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.todolist.dtos.CategoryDto;
import com.todolist.models.CategoryModel;
import com.todolist.models.UserModel;
import com.todolist.repositories.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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
}
