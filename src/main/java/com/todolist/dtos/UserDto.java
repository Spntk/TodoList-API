package com.todolist.dtos;

public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;

    public UserDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public UserDto(Long id, String name, String email, String passowrd) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = passowrd;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
