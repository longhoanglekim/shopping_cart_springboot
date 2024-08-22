package com.trainings.shoppingcartdemo.repositories;

import com.trainings.shoppingcartdemo.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    OrderDetails findByOrderId(Long id);
}
