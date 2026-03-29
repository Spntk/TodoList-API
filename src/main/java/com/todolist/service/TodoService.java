package com.todolist.service;

import com.todolist.repositories.CategoryRepository;
import com.todolist.repositories.TodoRepository;
import com.todolist.repositories.UserRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.todolist.dtos.CategoryDto;
import com.todolist.dtos.TodoDto;
import com.todolist.dtos.TodoRequestDto;
import com.todolist.models.CategoryModel;
import com.todolist.models.TodoModel;
import com.todolist.models.UserModel;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    TodoService(TodoRepository todoRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    private TodoDto toDto(TodoModel todo) {
        CategoryDto categoryDto = null;
        if (todo.getCategory() != null) {
            categoryDto = new CategoryDto(
                    todo.getCategory().getId(),
                    todo.getCategory().getCategoryName(),
                    todo.getCategory().getCreatedAt());
        }

        return new TodoDto(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                categoryDto,
                todo.getPriority().name(),
                todo.getStatus().name(),
                todo.getDueDate().toString());
    }

    @Transactional
    public List<TodoDto> getTodoById(Long userId) {
        return todoRepository.findByUserIdAndIsDeletedFalse(userId)
                .stream()
                .map(todo -> new TodoDto(
                        todo.getId(),
                        todo.getTitle(),
                        todo.getDescription(),
                        todo.getCategory() != null ? new CategoryDto(
                                todo.getCategory().getId(),
                                todo.getCategory().getCategoryName(),
                                todo.getCategory().getCreatedAt()) : null,
                        todo.getPriority().name(),
                        todo.getStatus().name(),
                        todo.getDueDate().toString()))
                .toList();
    }

    public TodoDto createTodo(Long userId, TodoRequestDto todoRequestDto) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        CategoryModel category = null;
        if (todoRequestDto.getCategoryId() != null) {
            category = categoryRepository.findByIdAndUserId(todoRequestDto.getCategoryId(), userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        }

        TodoModel todo = new TodoModel();
        todo.setTitle(todoRequestDto.getTitle());
        todo.setDescription(todoRequestDto.getDescription());
        todo.setCategory(category);
        todo.setPriority(todoRequestDto.getPriority());
        todo.setStatus(todoRequestDto.getStatus());
        todo.setDueDate(todoRequestDto.getDueDate());
        todo.setUser(user);

        TodoModel saved = todoRepository.save(todo);
        return toDto(saved);
    }

    public TodoDto updateTodo(Long todoId, Long userId, TodoRequestDto todoRequestDto) {
        TodoModel todoModel = todoRepository.findByIdAndUserId(todoId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        CategoryModel category = null;

        if (todoRequestDto.getCategoryId() != null) {
            category = categoryRepository.findByIdAndUserId(todoRequestDto.getCategoryId(), userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        }

        todoModel.setTitle(todoRequestDto.getTitle());
        todoModel.setDescription(todoRequestDto.getDescription());
        todoModel.setCategory(category);
        todoModel.setPriority(todoRequestDto.getPriority());
        todoModel.setStatus(todoRequestDto.getStatus());
        todoModel.setDueDate(todoRequestDto.getDueDate());

        TodoModel saved = todoRepository.save(todoModel);
        return toDto(saved);
    }

    public void softDeleteTodo(Long todoId, Long userId) {
        TodoModel todoModel = todoRepository.findByIdAndUserId(todoId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        todoModel.setIsDeleted(true);
        todoModel.setDeletedAt(LocalDateTime.now().withNano(0));
        todoRepository.save(todoModel);
    }
}
