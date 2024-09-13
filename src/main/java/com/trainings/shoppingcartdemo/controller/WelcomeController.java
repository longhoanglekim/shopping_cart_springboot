package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.services.AccountService;
import com.trainings.shoppingcartdemo.services.JwtService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
@Controller
@Slf4j
public class WelcomeController {
    private final AccountService accountService;
    private final ProductAPIController productAPIController;
    private final JwtService jwtService;
    public WelcomeController(AccountService accountService, ProductAPIController productAPIController, JwtService jwtService) {
        this.accountService = accountService;
        this.productAPIController = productAPIController;
        this.jwtService = jwtService;
    }

    @RequestMapping(value = "welcome", method = RequestMethod.GET)
    public String goWelcomePage(HttpSession session,ModelMap map) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            session.setAttribute("username", username);
        }
        map.put("categories", productAPIController.getAllCategories());
        log.debug("Get welcome");
        return "welcome_user";
    }


}
