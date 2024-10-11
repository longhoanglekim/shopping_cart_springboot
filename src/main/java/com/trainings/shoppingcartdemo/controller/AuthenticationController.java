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
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/auth")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDto loginUserDto, HttpServletResponse response) {
        Account authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);
        Cookie jwtCookie = new Cookie("jwtToken", jwtToken);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(60 * 60 * 24);
        response.addCookie(jwtCookie);
        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);

    }
}