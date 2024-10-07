package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.utils.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(HttpServletResponse response, HttpServletRequest request) throws ServletException {
        log.debug("Get login");
        // Tạo một cookie mới có cùng tên với cookie chứa JWT
        request.logout();
        JwtUtil.clearJwtTokenCookie(response);
        return "login";
    }

}
