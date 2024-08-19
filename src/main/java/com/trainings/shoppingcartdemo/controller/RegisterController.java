package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.models.Account;
import com.trainings.shoppingcartdemo.repositories.AccountRepository;
import com.trainings.shoppingcartdemo.services.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class RegisterController {
    private final AccountRepository accountRepository;
    private AccountService accountService;

    public RegisterController(AccountRepository accountRepository, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    @GetMapping("register")
    public String goRegisterPage() {
        return "register";
    }

    @PostMapping("register")
    public String register(@RequestParam String username, @RequestParam String password,
                           @RequestParam String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            log.debug("Password not match");
            return "redirect:/register";
        }
        if (accountRepository.findByUsername(username) != null) {
            log.debug("Username already exists");
            return "redirect:/register";
        }
        Account account = accountService.createAccount(username, password);
        accountRepository.save(account);
        return "redirect:/login";
    }
}
