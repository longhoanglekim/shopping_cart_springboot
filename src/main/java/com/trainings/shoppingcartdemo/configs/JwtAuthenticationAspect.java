package com.trainings.shoppingcartdemo.configs;

import com.trainings.shoppingcartdemo.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
@Slf4j
@Aspect
@Component
public class JwtAuthenticationAspect {

    private final JwtService jwtService;
    private final HttpServletRequest request;

    public JwtAuthenticationAspect(JwtService jwtService, HttpServletRequest request) {
        this.jwtService = jwtService;
        this.request = request;
    }

    // Áp dụng trước khi thực thi các phương thức có @RequiresAuth
    @Before("execution(* com.trainings.shoppingcartdemo.controller.*.*(..)) && @annotation(com.trainings.shoppingcartdemo.configs.RequiresAuth)")
    public void authenticate() throws Exception {
        log.debug("Authentication request");
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new Exception("JWT Token is missing");
        }

        String token = authorizationHeader.substring(7);
        if (!jwtService.isTokenValid(token)) {
            throw new Exception("Invalid JWT Token");
        }
    }
}