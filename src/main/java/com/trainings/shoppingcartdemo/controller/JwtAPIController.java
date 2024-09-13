package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.dto.UsernameDto;
import com.trainings.shoppingcartdemo.services.JwtService;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
@RestController
public class JwtAPIController {
    private final JwtService jwtService;

    public JwtAPIController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    // Sửa phương thức để lấy token từ Authorization header
    @GetMapping("api/jwt/getName")
    public UsernameDto getName(@RequestHeader("Authorization") String authHeader, HttpServletRequest request) {
        // Tách "Bearer " ra để chỉ lấy token
        String token = authHeader.substring(7); // Loại bỏ "Bearer " từ chuỗi
        String name = jwtService.extractUsername(token);
        request.setAttribute("name", name);
        return new UsernameDto(jwtService.extractUsername(token)); // Trả về tên người dùng từ token
    }
}
