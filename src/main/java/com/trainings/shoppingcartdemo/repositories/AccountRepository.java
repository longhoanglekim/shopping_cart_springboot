package com.trainings.shoppingcartdemo.repositories;

import com.trainings.shoppingcartdemo.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsernameAndPassword(String username, String password);
    Account findByUsername(String username);

    @Query("select a from accounts a where a.username like lower(concat('%',:keyword, '%'))")
    List<Account> search(String keyword);
}
