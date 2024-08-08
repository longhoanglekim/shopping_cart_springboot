package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.models.Account;
import com.trainings.shoppingcartdemo.models.Order;
import com.trainings.shoppingcartdemo.models.Product;
import com.trainings.shoppingcartdemo.repositories.AccountRepository;
import com.trainings.shoppingcartdemo.repositories.OrderRepository;
import com.trainings.shoppingcartdemo.repositories.ProductRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
public class OrderController {
    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;

    public OrderController(OrderRepository orderRepository, AccountRepository accountRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.accountRepository = accountRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/shopping_cart")
    public String goShoppingCartPage(HttpSession session,
                                     ModelMap map) {
        Order order = findCurrentOrder(session);
        orderRepository.save(order);
        List<Product> productList = productRepository.findByOrder(order);
        if (productList.isEmpty())
            return "empty_cart";
        map.put("productList", productList);
        return "product_cart";
    }

    @PostMapping("/addProductToCart")
    public String addProductToCart(@RequestParam("id") String id,
                                   HttpSession session) {

        Order order = findCurrentOrder(session);
        Product product = productRepository.findById(Long.parseLong(id)).get();
        product.setOrder(order);
        orderRepository.save(order);
        productRepository.save(product);
        log.debug("POST /addProduct with id: " + id);
        return "redirect:/productInfo?id=" + id;
    }

    private Order findCurrentOrder(HttpSession session) {
        // Find the order with the maximum id for the given account
        String username = (String) session.getAttribute("username");
        Account account = accountRepository.findByUsername(username);
        Order order = orderRepository.findByAccountAndIsCompletedFalse(account);
        if (order == null) {
            order = new Order();
            session.setAttribute("order", order);
            order.setAccount(account);
        }
        return order;
    }
}
