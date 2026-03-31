package com.todolist.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.todolist.dtos.UpdatePasswordRequest;
import com.todolist.dtos.UpdateProfileRequest;
import com.todolist.dtos.UpdateProfileResponse;
import com.todolist.dtos.UserDto;
import com.todolist.models.UserModel;
import com.todolist.models.enums.UserStatus;
import com.todolist.repositories.UserRepository;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UpdateProfileResponse updateProfile(Long userId, UpdateProfileRequest request) {
        UserModel userModel = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (userRepository.existsByEmail(request.getEmail())
                && !userModel.getEmail().equals(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        userModel.setName(request.getName());
        userModel.setEmail(request.getEmail());
        UserModel saved = userRepository.save(userModel);

        String newToken = jwtService.generateToken(saved, false);
        return new UpdateProfileResponse(
                newToken,
                new UserDto(saved.getId(), saved.getName(), saved.getEmail()));
    }

    public void updatePassword(Long userId, UpdatePasswordRequest request) {
        UserModel userModel = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), userModel.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password is incorrect");
        }

        userModel.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(userModel);
    }

    public void deactivateUser(Long userId) {
        UserModel userModel = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        userModel.setStatus(UserStatus.INACTIVE);
        userRepository.save(userModel);
    }
}
