package com.todolist.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.todolist.dtos.DashboardDto;
import com.todolist.dtos.TodoDto;
import com.todolist.models.TodoModel;
import com.todolist.models.enums.TodoStatus;
import com.todolist.repositories.TodoRepository;

import jakarta.transaction.Transactional;

@Service
public class DashboardService {

    private final TodoRepository todoRepository;
    private final TodoService todoService;

    public DashboardService(TodoRepository todoRepository, TodoService todoService) {
        this.todoRepository = todoRepository;
        this.todoService = todoService;
    }

    @Transactional
    public DashboardDto getDashboard(Long userId) {
        List<TodoModel> todoModels = todoRepository.findByUserIdAndIsDeletedFalse(userId);
        String today = LocalDate.now().toString();

        long total = todoModels.size();
        long completed = todoModels.stream().filter(t -> t.getStatus() == TodoStatus.COMPLETED).count();
        long inProgress = todoModels.stream().filter(t -> t.getStatus() == TodoStatus.IN_PROGRESS).count();
        long pending = todoModels.stream().filter(t -> t.getStatus() == TodoStatus.PENDING).count();
        long overdue = todoModels.stream()
                .filter(t -> t.getStatus() != TodoStatus.COMPLETED && t.getDueDate().toString().compareTo(today) < 0)
                .count();

        List<TodoDto> recentTasks = todoModels.stream().sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .limit(5).map(todoService::toDto).toList();

        List<TodoDto> overdueTodoList = todoModels.stream()
                .filter(t -> t.getStatus() != TodoStatus.COMPLETED && t.getDueDate().toString().compareTo(today) < 0)
                .map(todoService::toDto).toList();

        return new DashboardDto(total, completed, inProgress, pending, overdue, recentTasks, overdueTodoList);
    }
}
