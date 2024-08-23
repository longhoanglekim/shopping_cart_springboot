package com.trainings.shoppingcartdemo.repositories;

import com.trainings.shoppingcartdemo.models.Order;
import com.trainings.shoppingcartdemo.models.OrderProduct;
import com.trainings.shoppingcartdemo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    OrderProduct findOrderProductByOrderIdAndProductId(Long orderId, Long productId);
    void deleteOrderProductByOrderAndProduct(Order order, Product product);
    void deleteByProductId(Long productId);
    List<OrderProduct> findAllByOrder(Order order);
    boolean existsByOrderAndProduct(Order order, Product product);

    List<OrderProduct> findAllByProductId(Long id);
}