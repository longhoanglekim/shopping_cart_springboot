package com.trainings.shoppingcartdemo.services;

import com.trainings.shoppingcartdemo.models.Order;
import com.trainings.shoppingcartdemo.models.OrderProduct;
import com.trainings.shoppingcartdemo.models.Product;
import com.trainings.shoppingcartdemo.repositories.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderProductService {
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private OrderService orderService;

    public int getQuantityOfProductInOrder(Long orderId, Long productId) {
        OrderProduct orderProduct = orderProductRepository.findOrderProductByOrderIdAndProductId(orderId, productId);
        if (orderProduct == null) {
            return 0; // or handle the null case appropriately
        }
        return orderProduct.getQuantity();
    }

    public Map<Product, Integer> getProductMap(Order order) {
        List<Product> productList = orderService.getProductListOfAnOrder(order);
        if (productList.isEmpty()) {
            return null;
        }
        Map<Product, Integer> productMap = new HashMap<>();
        for (Product product : productList) {
            if (getQuantityOfProductInOrder(order.getId(), product.getId()) > 0) {
                productMap.put(product, getQuantityOfProductInOrder(order.getId(), product.getId()));
            }
        }
        return productMap;
    }
}
