package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.models.*;
import com.trainings.shoppingcartdemo.repositories.*;
import com.trainings.shoppingcartdemo.services.PriceFormattingService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
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
    private final AccountDetailsRepository accountDetailsRepository;
    private final PriceFormattingService formattingService;
    private final OrderDetailsRepository orderDetailsRepository;

    public OrderController(OrderRepository orderRepository, AccountRepository accountRepository, ProductRepository productRepository, AccountDetailsRepository accountDetailsRepository, PriceFormattingService formattingService, OrderDetailsRepository orderDetailsRepository) {
        this.orderRepository = orderRepository;
        this.accountRepository = accountRepository;
        this.productRepository = productRepository;
        this.accountDetailsRepository = accountDetailsRepository;
        this.formattingService = formattingService;
        this.orderDetailsRepository = orderDetailsRepository;
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
        Map<Product, Integer> productMap;
        if (!isInUpdateProcess) {
            order = findCurrentOrder(session);
            orderRepository.save(order);
            productList = productRepository.findByOrder(order);
            productMap = getUnduplicatedProductList(productList);
            session.setAttribute("productMap", productMap);
        }
        productMap = (HashMap<Product, Integer>) session.getAttribute("productMap");
        BigDecimal totalVal = new BigDecimal(0);
        for (Map.Entry<Product, Integer> entry: productMap.entrySet()) {
            totalVal = totalVal.add(entry.getKey().getPrice()).multiply(new BigDecimal(entry.getValue()));
        }
        // call api to format price

        session.setAttribute("totalValue", formattingService.getFormattedPrice(totalVal) );
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



    private Map<Product, Integer> getUnduplicatedProductList(List<Product> productList) {
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

    @GetMapping("/confirmOrder")
    public String goConfirmPage(HttpSession session,
                                ModelMap map) {
        Account account = accountRepository.findByUsername((String) session.getAttribute("username"));
        AccountDetails accountDetails = accountDetailsRepository.findByAccountId(account.getId());
        OrderDetails orderDetails = orderDetailsRepository.findByOrderId(findCurrentOrder(session).getId());
        map.put("accountDetails", accountDetails);
        map.put("orderDetails", orderDetails);
        return "confirm_order";
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
        BigDecimal totalVal = new BigDecimal(0);
        for (Map.Entry<Product, Integer> entry: cart.entrySet()) {
            totalVal = totalVal.add(entry.getKey().getPrice()).multiply(new BigDecimal(entry.getValue()));
        }
        log.debug("Total value: " + totalVal);
        session.setAttribute("totalValue", totalVal);
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            log.debug(entry.getKey().getName() + " :" + entry.getValue());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("totalValue", totalVal);
        return ResponseEntity.ok("{\"status\":\"success\"}");
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
            order.setIsCompleted(false);
            orderRepository.save(order);
        }
        return order;
    }
}