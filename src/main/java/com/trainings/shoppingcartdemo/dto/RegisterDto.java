package com.trainings.shoppingcartdemo.dto;

import lombok.Getter;

@Getter
public class RegisterDto {
    private String username;
    private String password;


    public RegisterDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}