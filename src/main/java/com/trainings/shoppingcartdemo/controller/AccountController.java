package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.models.Account;
import com.trainings.shoppingcartdemo.repositories.AccountRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class AccountController {
    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping
    public String goProfilePage(HttpSession session, ModelMap map) {
        Account account = accountRepository.findByUsername((String) session.getAttribute("username"));
        map.put("account", account);
        return "account_profile";
    }


}
