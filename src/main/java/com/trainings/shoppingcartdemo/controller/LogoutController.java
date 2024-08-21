package com.trainings.shoppingcartdemo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/logout")
    public String logout() {
        log.debug("Get logout");
        return "logout_confirm";
    }

    @PostMapping("/logout")
    public String logoutPost(HttpServletRequest request, HttpServletResponse response) {
        log.debug("POST /logout");
        request.getSession().invalidate();
        return "redirect:/login";
    }
}