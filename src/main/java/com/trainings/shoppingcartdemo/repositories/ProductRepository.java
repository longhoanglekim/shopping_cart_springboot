package com.trainings.shoppingcartdemo.repositories;

import com.trainings.shoppingcartdemo.models.Account;
import com.trainings.shoppingcartdemo.models.Order;
import com.trainings.shoppingcartdemo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long > {
    List<Product> findByCategory(String category);
    List<Product> findAllByCategory(String category);
    int countProductByNameAndPriceAndAccount(String productName, BigDecimal price, Account account);
    List<Product> getProductsByNameAndPrice(String productName, BigDecimal name);

    @Query("SELECT p FROM products p WHERE (lower(p.name) LIKE lower(concat('% ', :keyword, '%')) OR lower(p.name) LIKE lower(concat('', :keyword, '%'))) and p.isPurchased is false")
    List<Product> search(String keyword);

    @Query("select distinct p.category from products p")
    List<String> getCategoriesList();
}
