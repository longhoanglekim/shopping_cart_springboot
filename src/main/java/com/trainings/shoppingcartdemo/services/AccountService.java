package com.trainings.shoppingcartdemo.services;

import com.trainings.shoppingcartdemo.models.Account;
import com.trainings.shoppingcartdemo.models.AccountDetails;
import com.trainings.shoppingcartdemo.repositories.AccountDetailsRepository;
import com.trainings.shoppingcartdemo.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountDetailsRepository accountDetailsRepository;

    @Transactional
    public Account createAccount(String username, String password) {
        // Create Account
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        // Create AccountDetails
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAddress("Default Address");
        accountDetails.setAccount(account);

        // Associate AccountDetails with Account
        account.setAccountDetail(accountDetails);

        // Save Account (will automatically save AccountDetails due to cascading)
        return accountRepository.save(account);
    }
}
