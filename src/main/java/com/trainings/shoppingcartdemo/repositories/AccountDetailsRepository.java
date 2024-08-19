package com.trainings.shoppingcartdemo.repositories;

import com.trainings.shoppingcartdemo.models.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDetailsRepository extends JpaRepository<AccountDetails, Long> {
    AccountDetails findByAccountId(Long accountId);
}
