package com.trainings.shoppingcartdemo.repositories;

import com.trainings.shoppingcartdemo.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsernameAndPassword(String username, String password);
}
