package com.trainings.shoppingcartdemo.dto;

import lombok.Getter;

@Getter
public class    UsernameDto {
    private String username;

    public UsernameDto() {
    }

    public UsernameDto(String username) {
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}