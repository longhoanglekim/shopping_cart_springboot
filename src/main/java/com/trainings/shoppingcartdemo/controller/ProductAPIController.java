package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.models.Product;
import com.trainings.shoppingcartdemo.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
@Slf4j
@RestController
public class ProductAPIController {
    private final ProductRepository productRepository;

    public ProductAPIController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/api/product/categories")
    public List<String> getAllCategories() {
        for (String category : productRepository.getCategoriesList()) {
            log.debug(category);
        }
        return productRepository.getCategoriesList();
    }

    @GetMapping("api/product/categories/productList/{category}")
    public List<Product> getProductListByCategory(@PathVariable String category) {
        List<Product> productList = productRepository.findByCategory(category);
        return productList;
    }
}
