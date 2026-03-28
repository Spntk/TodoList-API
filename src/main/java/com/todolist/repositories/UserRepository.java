package com.todolist.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todolist.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    boolean existsByEmail(String email);

    Optional<UserModel> findByEmail(String email);
}
