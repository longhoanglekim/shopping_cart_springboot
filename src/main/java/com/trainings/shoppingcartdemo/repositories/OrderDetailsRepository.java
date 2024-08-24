package com.trainings.shoppingcartdemo.repositories;

import com.trainings.shoppingcartdemo.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    OrderDetails findByOrderId(Long id);
    List<OrderDetails> getOrderDetailsByState(String state);
}
