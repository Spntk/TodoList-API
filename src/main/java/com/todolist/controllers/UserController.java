package com.todolist.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.dtos.UpdatePasswordRequest;
import com.todolist.dtos.UpdateProfileRequest;
import com.todolist.dtos.UpdateProfileResponse;
import com.todolist.dtos.UserDto;
import com.todolist.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/profile")
    public ResponseEntity<UpdateProfileResponse> updateProfile(HttpServletRequest request,
            @RequestBody @Valid UpdateProfileRequest body) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(userService.updateProfile(userId, body));
    }

    @PutMapping("/password")
    public ResponseEntity<UserDto> updatePassword(HttpServletRequest request,
            @RequestBody @Valid UpdatePasswordRequest body) {
        Long userId = (Long) request.getAttribute("userId");
        userService.updatePassword(userId, body);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/deactivate")
    public ResponseEntity<Void> deactivateUser(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        userService.deactivateUser(userId);
        return ResponseEntity.noContent().build();
    }
}
