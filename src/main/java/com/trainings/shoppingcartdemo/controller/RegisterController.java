package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.models.Account;
import com.trainings.shoppingcartdemo.repositories.AccountRepository;
import com.trainings.shoppingcartdemo.services.AccountService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    private final AccountRepository accountRepository;
    private AccountService accountService;


    public RegisterController(AccountRepository accountRepository, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;

    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";  // Trả về trang đăng ký
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Model model) {
        // Kiểm tra xem người dùng đã tồn tại hay chưa
        if (accountRepository.findByUsername(username) != null) {
            model.addAttribute("message", "Username already exists!");
            return "register";  // Trả về trang đăng ký với thông báo lỗi
        }


        accountService.createAccount(username, password);


        model.addAttribute("message", "User registered successfully!");

        // Chuyển hướng tới trang đăng nhập sau khi đăng ký thành công
        return "redirect:/login";
    }
}
