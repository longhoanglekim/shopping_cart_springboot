package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.configs.RequiresAuth;
import com.trainings.shoppingcartdemo.models.Account;
import com.trainings.shoppingcartdemo.repositories.AccountRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Slf4j
@Controller
public class AccountController {
    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequiresAuth
    public String goProfilePage() {
        log.debug("Profile req");
        return "account_profile";
    }


}
