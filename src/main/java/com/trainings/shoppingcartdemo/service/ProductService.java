package com.trainings.shoppingcartdemo.service;

import com.trainings.shoppingcartdemo.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Service
public class ProductService {
    private static List<Product> productList = new ArrayList<>();
    static {
        productList.add(new Product(1, "gaming mouse", "play game well", new BigDecimal(1000000)));
        productList.add(new Product(2, "gaming laptop", "play game well", new BigDecimal(20000000)));
        productList.add(new Product(3, "calculator", "calculate well", new BigDecimal(500000)));
    }

    public List<Product> getProductList() {
        return productList;
    }
}
