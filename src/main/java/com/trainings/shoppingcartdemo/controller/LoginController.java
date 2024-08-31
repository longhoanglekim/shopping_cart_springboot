package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.models.Account;
import com.trainings.shoppingcartdemo.repositories.AccountRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Slf4j
@SessionAttributes("username")
@Controller
public class LoginController {
    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    public LoginController(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("login")
    public String goLoginPage() {
        log.debug("Get login");
        return "login";
    }

}