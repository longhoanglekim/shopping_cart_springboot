package com.trainings.shoppingcartdemo.dto;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public class LoginDto {
    private String username;
    private String password;

    public LoginDto() {
    }

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Map<String, Object> getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserDetails getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
