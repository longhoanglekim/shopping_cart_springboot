package com.trainings.shoppingcartdemo.dto;

import lombok.Getter;

@Getter
public class JwtTokenDto {
    private String token;

    public JwtTokenDto() {
    }

    public JwtTokenDto(String token) {
        this.token = token;

    }

    public JwtTokenDto setToken(String token) {
        this.token = token;
        return this;
    }
}
