package com.trainings.shoppingcartdemo.services;


import com.trainings.shoppingcartdemo.models.Account;
import com.trainings.shoppingcartdemo.models.AccountDetails;
import com.trainings.shoppingcartdemo.repositories.AccountDetailsRepository;
import com.trainings.shoppingcartdemo.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountDetailsRepository accountDetailsRepository;

    @Autowired
    private JwtService jwtService;

    @Transactional
    public Account createAccount(String username, String password) {
        // Create Account
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setRole("ROLE_USER");
        account.setEnabled(true);
        // Create AccountDetails
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAddress("Default Address");
        accountDetails.setAccount(account);

        // Associate AccountDetails with Account
        account.setAccountDetail(accountDetails);

        // Save Account (will automatically save AccountDetails due to cascading)
        accountRepository.save(account);
        accountDetailsRepository.save(accountDetails);
        return account;
    }

    public Account getCurrentAccount() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return accountRepository.findByUsername(username);
    }

    public List<Account> allAccounts() {
        return accountRepository.findAll();
    }

    public Account getCurrentAccount(String token) {
        String username = jwtService.extractUsername(token);
        return accountRepository.findByUsername(username);
    }
}
