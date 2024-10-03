package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.models.Account;
import com.trainings.shoppingcartdemo.repositories.AccountRepository;
import com.trainings.shoppingcartdemo.services.AccountService;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Slf4j
@Controller
public class AccountController {
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    public AccountController(AccountRepository accountRepository, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String goProfilePage(ModelMap map) {
        log.debug("Profile req");

        Account account = accountRepository.findByUsername("long");
        map.put("cash", account.getCashInWallet());
        return "account_profile";
    }




}
