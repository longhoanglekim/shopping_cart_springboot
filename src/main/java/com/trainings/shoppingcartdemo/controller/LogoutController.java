package com.trainings.shoppingcartdemo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
  This class is used to handle the logout process
  Change the return of each mapping to get the flow you want.
 */
@Slf4j
@Controller
public class LogoutController {

    @GetMapping("logout-success")
    public String goSuccessLogoutPage() {
        log.info("GET /logout-success");
        return "logout_success";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        log.info("GET /logout");
        return "logout_confirm";
    }
    @PostMapping("/logout")
    public String logoutPost(HttpServletRequest request, HttpServletResponse response) {
        log.info("POST /logout");
        return "redirect:/logout-success";
    }
}