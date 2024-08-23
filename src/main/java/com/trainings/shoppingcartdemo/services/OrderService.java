package com.trainings.shoppingcartdemo.services;

import com.trainings.shoppingcartdemo.models.*;
import com.trainings.shoppingcartdemo.repositories.OrderDetailsRepository;
import com.trainings.shoppingcartdemo.repositories.OrderProductRepository;
import com.trainings.shoppingcartdemo.repositories.OrderRepository;
import com.trainings.shoppingcartdemo.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Product> getProductListOfAnOrder(Order order) {
        List<OrderProduct> orderProductList = orderProductRepository.findAllByOrder(order);
        List<Product> productList = new ArrayList<>();
        for (OrderProduct orderProduct : orderProductList) {
            productList.add(orderProduct.getProduct());
        }
        return productList;
    }

    @Transactional
    public Order createOrder(Account account) {
        Order order = new Order();
        order.setAccount(account);
        order.setCompleted(false);

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrder(order);
        order.setOrderDetails(orderDetails);
        orderDetailsRepository.save(orderDetails);

        return orderRepository.save(order);
    }

    @Transactional
    public void addProductToCart(Order order, Product product, int quantity) {
        // Check if the product already exists in the order
        OrderProduct orderProduct = orderProductRepository.findOrderProductByOrderIdAndProductId(order.getId(), product.getId());
        if (orderProduct != null) {
            log.warn("Product {} is already in the order {}", product.getName(), order.getId());
            return;
        }

        // Create new OrderProduct and set properties
        orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setOrder(order);
        orderProduct.setQuantity(quantity); // Set the provided quantity

        // Update associations
        product.getOrderProducts().add(orderProduct);
        order.getOrderProducts().add(orderProduct);

        // Save the orderProduct, order, and product
        orderProductRepository.save(orderProduct);
        productRepository.save(product);
        orderRepository.save(order);

        log.info("Added {} units of {} to order {}", quantity, product.getName(), order.getId());
    }




    @Transactional
    public void removeProductFromCart(Order order, Product product) {
        // Find the OrderProduct that links the given order and product
        OrderProduct orderProduct = orderProductRepository.findOrderProductByOrderIdAndProductId(order.getId(), product.getId());

        if (orderProduct != null) {
            // Remove the OrderProduct from both the order and product lists
            order.getOrderProducts().remove(orderProduct);
            product.getOrderProducts().remove(orderProduct);

            // Delete the OrderProduct entity
            orderProductRepository.delete(orderProduct);

            // Save the updated order and product
            orderRepository.save(order);
            productRepository.save(product);

            log.info("Removed product {} from order {}", product.getName(), order.getId());
        } else {
            log.warn("Product {} not found in order {}", product.getName(), order.getId());
        }
    }

    public boolean containsProduct(Order order, Product product) {
        return orderProductRepository.existsByOrderAndProduct(order, product);
    }

}
