package com.trainings.shoppingcartdemo.repositories;

import com.trainings.shoppingcartdemo.models.Account;
import com.trainings.shoppingcartdemo.models.Order;
import com.trainings.shoppingcartdemo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByAccountId(Long accountId);
    // Find the order with the maximum id for the given account
    Order findByAccountAndIsCompletedFalse(Account account);
}
