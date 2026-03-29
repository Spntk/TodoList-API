package com.todolist.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todolist.models.TodoModel;

public interface TodoRepository extends JpaRepository<TodoModel, Long> {
    List<TodoModel> findByUserIdAndIsDeletedFalse(Long userId);

    void deleteByIsDeletedTrueAndDeletedAtBefore(LocalDateTime expiredDate);

    Optional<TodoModel> findByIdAndUserId(Long id, Long userId);
}
