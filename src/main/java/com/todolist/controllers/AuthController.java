package com.todolist.controllers;

import com.todolist.service.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.todolist.dtos.AuthDto;
import com.todolist.models.UserModel;
import com.todolist.models.enums.UserStatus;
import com.todolist.repositories.UserRepository;
import com.todolist.service.JwtService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final CategoryService categoryService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder,
            CategoryService categoryService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.categoryService = categoryService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signUp(@RequestBody AuthDto authDto) {

        if (userRepository.existsByEmail(authDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This email is already exists");
        }

        UserModel user = new UserModel();

        user.setName(authDto.getName());
        user.setEmail(authDto.getEmail());
        user.setPassword(passwordEncoder.encode(authDto.getPassword()));

        userRepository.save(user);
        categoryService.createDefualtCategories(user);

        String token = jwtService.generateToken(user, false);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("name", user.getName());
        response.put("message", "Register Successful");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<Map<String, String>> signIn(@RequestBody AuthDto authDto) {
        UserModel user = userRepository.findByEmail(authDto.getEmail())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email or Password is not valid"));

        boolean isMatch = passwordEncoder.matches(authDto.getPassword(), user.getPassword());
        if (!isMatch) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email or Password is not valid");
        }

        if (user.getStatus() == UserStatus.INACTIVE) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Your account has been suspended, Please contact admin");
        }

        String token = jwtService.generateToken(user, authDto.isKeepSignedIn());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("id", user.getId().toString());
        response.put("role", user.getRole().toString());
        response.put("name", user.getName());
        response.put("message", "Login Successful");

        return ResponseEntity.ok(response);
    }

}
