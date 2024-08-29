package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.services.AccountService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Controller
@Slf4j
public class WelcomeController {
    private final AccountService accountService;

    public WelcomeController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "welcome", method = RequestMethod.GET)
    public String goWelcomePage(HttpSession session) {
        // System.out.println(session.getAttribute("username"));
        if (accountService.getCurrentAccount() != null) {
            session.setAttribute("username", accountService.getCurrentAccount().getUsername());
        }
        log.debug("Get welcome");
        return "welcome_user";
    }


}
