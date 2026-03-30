package com.todolist.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.todolist.models.TodoModel;

import jakarta.transaction.Transactional;

public interface TodoRepository extends JpaRepository<TodoModel, Long> {
    List<TodoModel> findByUserIdAndIsDeletedFalse(Long userId);

    List<TodoModel> findByUserIdAndIsDeletedTrue(Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM TodoModel t WHERE t.isDeleted = true AND t.deletedAt < :expiredDate")
    void deleteByIsDeletedTrueAndDeletedAtBefore(LocalDateTime expiredDate);

    Optional<TodoModel> findByIdAndUserId(Long id, Long userId);

    Optional<TodoModel> findByIdAndUserIdAndIsDeletedTrue(Long id, Long userId);

    Optional<TodoModel> findByIdAndUserIdAndIsDeletedFalse(Long id, Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE TodoModel t SET t.category = NULL WHERE t.category.id = :categoryId")
    void updateCategoryToNull(@Param("categoryId") Long categoryId);
}
