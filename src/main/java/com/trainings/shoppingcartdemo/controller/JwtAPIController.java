package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.dto.JwtTokenDto;
import com.trainings.shoppingcartdemo.dto.UsernameDto;
import com.trainings.shoppingcartdemo.services.JwtService;
import com.trainings.shoppingcartdemo.utils.JwtUtil;
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
    public UsernameDto getName(HttpServletRequest request) {
        // Tách "Bearer " ra để chỉ lấy token
        String token = JwtUtil.getToken(request);
        String name = jwtService.extractUsername(token);
        request.setAttribute("name", name);
        return new UsernameDto(jwtService.extractUsername(token)); // Trả về tên người dùng từ token
    }

    @GetMapping("api/jwt/getToken")
    public JwtTokenDto getToken(HttpServletRequest request) {
        String token = JwtUtil.getToken(request);
        if (token != null )
            return new JwtTokenDto(token);
        return null;
    }
}
