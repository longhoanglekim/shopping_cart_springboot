package com.trainings.shoppingcartdemo.services;

import com.trainings.shoppingcartdemo.models.Order;
import com.trainings.shoppingcartdemo.models.Product;
import com.trainings.shoppingcartdemo.repositories.OrderRepository;
import com.trainings.shoppingcartdemo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Transactional
    public void addProductToCart(Order order, Product product) {
        List<Product> productList = order.getProductList();
        productList.add(product);
        order.setProductList(productList);
        product.setOrder(order);
        orderRepository.save(order);
        productRepository.save(product);
    }


    @Transactional
    public void removeProductFromCart(Order order, Product product) {
        order.getProductList().remove(product);
        orderRepository.save(order);
        product.setOrder(null);
        productRepository.save(product);
    }
}
