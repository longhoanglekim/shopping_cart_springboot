package com.trainings.shoppingcartdemo.service;

import com.trainings.shoppingcartdemo.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Service
public class ProductService {
    static int count = 0;
    private static List<Product> productList = new ArrayList<>();
    static {
        productList.add(new Product(++count, "gaming mouse", "play game well", "PC and laptop", new BigDecimal(1000000)));
        productList.add(new Product(++count, "gaming laptop", "play game well", "PC and laptop", new BigDecimal(20000000)));
        productList.add(new Product(++count, "calculator", "calculate well", "Electronic device", new BigDecimal(500000)));
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void addProduct(String name, String description, String category,BigDecimal price) {
        productList.add(new Product(++count, name, description, category,price));
    }
}
