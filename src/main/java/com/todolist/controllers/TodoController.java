package com.todolist.controllers;

import com.todolist.service.TodoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.dtos.TodoDto;
import com.todolist.dtos.TodoRequestDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<List<TodoDto>> getTodoById(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(todoService.getTodoById(userId));
    }

    @PostMapping
    public ResponseEntity<TodoDto> createTodo(HttpServletRequest request,
            @RequestBody @Valid TodoRequestDto todoRequestDto) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(todoService.createTodo(userId, todoRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTodo(HttpServletRequest request, @PathVariable Long id,
            @RequestBody @Valid TodoRequestDto todoRequestDto) {

        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(todoService.updateTodo(id, userId, todoRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        todoService.softDeleteTodo(id, userId);
        return ResponseEntity.noContent().build();
    }
}
