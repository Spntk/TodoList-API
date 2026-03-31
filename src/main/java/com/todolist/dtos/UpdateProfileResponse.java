package com.todolist.dtos;

public class UpdateProfileResponse {
    private String token;
    private UserDto user;

    public UpdateProfileResponse(String token, UserDto user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public UserDto getUser() {
        return user;
    }
}
