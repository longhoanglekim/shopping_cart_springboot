package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.dto.LoginDto;
import com.trainings.shoppingcartdemo.dto.LoginResponse;
import com.trainings.shoppingcartdemo.dto.RegisterDto;
import com.trainings.shoppingcartdemo.models.Account;
import com.trainings.shoppingcartdemo.security.jwt.JwtService;
import com.trainings.shoppingcartdemo.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Account> register(@RequestBody RegisterDto registerAccountDto) {
        Account registeredAccount = authenticationService.signup(registerAccountDto);

        return ResponseEntity.ok(registeredAccount);
    }

//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDto loginAccountDto) {
//        Account authenticatedAccount = authenticationService.authenticate(loginAccountDto);
//
//        String jwtToken = jwtService.generateToken(authenticatedAccount);
//        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
//        return ResponseEntity.ok(loginResponse);
//    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDto loginUserDto) {
        Account authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}