package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.dto.LoginDto;
import com.trainings.shoppingcartdemo.dto.LoginResponse;
import com.trainings.shoppingcartdemo.dto.RegisterDto;
import com.trainings.shoppingcartdemo.models.Account;
import com.trainings.shoppingcartdemo.services.JwtService;
import com.trainings.shoppingcartdemo.services.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody RegisterDto registerAccountDto) {
        // register new account with encoded password
        Account newAccount = authenticationService.signup(registerAccountDto);
        if (newAccount == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(newAccount);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginRequest, HttpServletResponse response) {
        // Tạo token
        String token = jwtService.generateToken(loginRequest.getUsername(), loginRequest.getPassword());

        // Tạo cookie chứa token
        Cookie jwtCookie = new Cookie("jwtToken", token);
        jwtCookie.setHttpOnly(true); // Đảm bảo cookie không thể bị truy cập bởi JavaScript
        jwtCookie.setSecure(true); // Cookie chỉ được gửi qua HTTPS
        jwtCookie.setPath("/"); // Cookie có hiệu lực cho toàn bộ ứng dụng
        jwtCookie.setMaxAge(60 * 60 * 24); // Cookie hết hạn sau 1 ngày

        // Thêm cookie vào response
        response.addCookie(jwtCookie);

        // Trả về response thành công
        return ResponseEntity.ok("Login successful");
    }

}