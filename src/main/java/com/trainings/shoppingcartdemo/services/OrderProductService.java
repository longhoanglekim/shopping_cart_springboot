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

    public int getQuantityOfProductInOrder(Long orderId, Long productId) {
        OrderProduct orderProduct = orderProductRepository.findOrderProductByOrderIdAndProductId(orderId, productId);
        if (orderProduct == null) {
            return 0; // or handle the null case appropriately
        }
        return orderProduct.getQuantity();
    }


}
