package com.trainings.shoppingcartdemo.services;

import com.trainings.shoppingcartdemo.models.Order;
import com.trainings.shoppingcartdemo.models.OrderProduct;
import com.trainings.shoppingcartdemo.models.Product;
import com.trainings.shoppingcartdemo.repositories.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderProductService {
    @Autowired
    private OrderProductRepository orderProductRepository;

    public int getQuantityOfProductInOrder(Order order, Product product) {
        OrderProduct orderProduct = orderProductRepository.findOrderProductByOrderIdAndProductId(order.getId(), product.getId());
        return orderProduct.getQuantity();
    }


}
