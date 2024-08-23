package com.trainings.shoppingcartdemo.repositories;

import com.trainings.shoppingcartdemo.models.Order;
import com.trainings.shoppingcartdemo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long > {
    List<Product> findByCategory(String category);

}
