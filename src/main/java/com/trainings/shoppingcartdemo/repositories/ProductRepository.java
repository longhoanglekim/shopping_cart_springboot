package com.trainings.shoppingcartdemo.repositories;

import com.trainings.shoppingcartdemo.models.Order;
import com.trainings.shoppingcartdemo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long > {
    List<Product> findByCategory(String category);
    int countProductByNameAndPrice(String productName, BigDecimal price);
    List<Product> getProductsByNameAndPrice(String productName, BigDecimal name);
}
