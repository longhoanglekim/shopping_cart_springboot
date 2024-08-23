package com.trainings.shoppingcartdemo.services;

import com.trainings.shoppingcartdemo.models.Order;
import com.trainings.shoppingcartdemo.models.OrderProduct;
import com.trainings.shoppingcartdemo.models.Product;
import com.trainings.shoppingcartdemo.repositories.OrderProductRepository;
import com.trainings.shoppingcartdemo.repositories.OrderRepository;
import com.trainings.shoppingcartdemo.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void addProductToCart(Order order, Product product) {
        List<Product> productList = order.getProductList();
        productList.add(product);
        order.setProductList(productList);
        product.setOrder(order);
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setOrder(order);
        orderProduct.setQuantity(1);
        orderRepository.save(order);
        productRepository.save(product);
        orderProductRepository.save(orderProduct);
    }


    @Transactional
    public void removeProductFromCart(Order order, Product product) {
        order.getProductList().remove(product);
        orderRepository.save(order);
        product.setOrder(null);
        productRepository.save(product);
    }

    public boolean containsProduct(Order order, Product product) {
        for (Product targetProduct : order.getProductList()) {
            log.debug(targetProduct.getName());
        }
        return order.getProductList().contains(product);
    }
}
