package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.models.Account;
import com.trainings.shoppingcartdemo.models.Order;
import com.trainings.shoppingcartdemo.models.Product;
import com.trainings.shoppingcartdemo.repositories.AccountRepository;
import com.trainings.shoppingcartdemo.repositories.OrderRepository;
import com.trainings.shoppingcartdemo.repositories.ProductRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class OrderController {
    static boolean isInUpdateProcess = false;
    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;

    public OrderController(OrderRepository orderRepository, AccountRepository accountRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.accountRepository = accountRepository;
        this.productRepository = productRepository;
    }

    /**
     *
     * @param session HttpSession
     *
     * @return the list of the products if not null
     */
    @GetMapping("/shopping_cart")
    public String goShoppingCartPage(HttpSession session) {
        log.debug("GET CART PAGE");
        Order order;
        List<Product> productList;
        if (!isInUpdateProcess) {
            order = findCurrentOrder(session);
            orderRepository.save(order);
            productList = productRepository.findByOrder(order);
            Map<Product, Integer> productMap = getUnduplicatedProductList(productList);
            session.setAttribute("productMap", productMap);
        }
        if (session.getAttribute("productMap") == null)
            return "empty_cart";
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

    Map<Product, Integer> getUnduplicatedProductList(List<Product> productList) {
        Map<Product, Integer> map = new HashMap<>();
        for (Product product : productList) {
            if (map.containsKey(product)) {
                map.put(product, map.get(product) + 1);
            } else {
                map.put(product, 1);
            }
        }
        return map;
    }


    @PostMapping("/update-quantity")
    public ResponseEntity<String> updateQuantity(@RequestParam String productName,
                                                 @RequestParam int quantity,
                                                 HttpSession session) {
        isInUpdateProcess = true;
        // Lấy giỏ hàng từ session
        Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("productMap");


        // Tìm và cập nhật số lượng sản phẩm
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            if (entry.getKey().getName().equals(productName)) {
                if (quantity == 0) {
                    cart.remove(entry.getKey());  // Xóa sản phẩm nếu số lượng bằng 0
                } else {
                    cart.put(entry.getKey(), quantity);  // Cập nhật số lượng mới
                    log.debug(String.valueOf(entry.getValue()));
                }
                break;
            }
        }

        session.setAttribute("productMap", cart);
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            log.debug(entry.getKey().getName() + " :" + entry.getValue());
        }
        return ResponseEntity.ok("{\"status\":\"success\"}");
    }
}
