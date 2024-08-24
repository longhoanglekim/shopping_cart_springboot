package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.enums.OrderState;
import com.trainings.shoppingcartdemo.models.*;
import com.trainings.shoppingcartdemo.repositories.*;
import com.trainings.shoppingcartdemo.services.OrderProductService;
import com.trainings.shoppingcartdemo.services.OrderService;
import com.trainings.shoppingcartdemo.services.PriceFormattingService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class OrderController {

    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;
    private final AccountDetailsRepository accountDetailsRepository;
    private final PriceFormattingService formattingService;
    private final OrderDetailsRepository orderDetailsRepository;
    private final OrderService orderService;
    private final OrderProductService orderProductService;
    private final OrderProductRepository orderProductRepository;
    private final PriceFormattingService priceFormattingService;

    public OrderController(OrderRepository orderRepository, AccountRepository accountRepository, ProductRepository productRepository, AccountDetailsRepository accountDetailsRepository, PriceFormattingService formattingService, OrderDetailsRepository orderDetailsRepository, OrderService orderService, OrderProductService orderProductService, OrderProductRepository orderProductRepository, PriceFormattingService priceFormattingService) {
        this.orderRepository = orderRepository;
        this.accountRepository = accountRepository;
        this.productRepository = productRepository;
        this.accountDetailsRepository = accountDetailsRepository;
        this.formattingService = formattingService;
        this.orderDetailsRepository = orderDetailsRepository;
        this.orderService = orderService;
        this.orderProductService = orderProductService;
        this.orderProductRepository = orderProductRepository;
        this.priceFormattingService = priceFormattingService;
    }

    @GetMapping("/shopping_cart")
    public String goShoppingCartPage(HttpSession session) {
        log.debug("GET /shopping_cart");
        String username = (String) session.getAttribute("username");
        Account account = accountRepository.findByUsername(username);
        Order order = orderRepository.findByAccountAndCompletedFalse(account);
        if (order == null) {
            order = orderService.createOrder(account);
        }
        session.setAttribute("order", order);
        Map<Product, Integer> productMap = orderProductService.getProductMap(order);
        if (productMap == null) {
            productMap = new HashMap<>();
        }
        session.setAttribute("productMap", productMap);

        BigDecimal totalVal = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : productMap.entrySet()) {
            totalVal = totalVal.add(entry.getKey().getPrice().multiply(new BigDecimal(entry.getValue())));
        }

        OrderDetails orderDetails = orderDetailsRepository.findByOrderId(order.getId());
        BigDecimal deliverPayment = BigDecimal.ZERO;
        if (orderDetails != null) {
            deliverPayment = orderDetails.getDeliverPayment();
        }
        BigDecimal totalPayment = totalVal.add(deliverPayment);
        session.setAttribute("totalPayment", priceFormattingService.getFormattedPrice(totalPayment));
        session.setAttribute("totalValue", priceFormattingService.getFormattedPrice(totalVal));

        log.debug("Total value: " + totalVal);
        return "product_cart";
    }

    @Transactional
    @PostMapping("/addProductToCart")
    public String addProductToCart(@RequestParam("id") String id, HttpSession session) {
        Order order = findCurrentOrder(session);
        Product product = productRepository.findById(Long.parseLong(id)).orElse(null);

        if (product != null) {
            if (!orderService.containsProduct(order, product)) {
                orderService.addProductToCart(order, product, 1); // Assuming quantity 1 for simplicity
                session.setAttribute("order", order);
            } else {
                log.debug("Product is already in the order");
            }
        } else {
            log.debug("Product not found with id: " + id);
        }

        log.debug("POST /addProduct with id: " + id);
        return "redirect:/productInfo?id=" + id;
    }

    @PostMapping("/update-quantity")
    public ResponseEntity<String> updateQuantity(@RequestParam String productName, @RequestParam int quantity, HttpSession session) {
        Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("productMap");
        Order order = (Order) session.getAttribute("order");

        if (cart == null || order == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"status\":\"Cart or Order not found\"}");
        }

        Product targetProduct = null;
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            if (entry.getKey().getName().equals(productName)) {
                targetProduct = entry.getKey();
                if (quantity == 0) {
                    // Remove product from cart and order if quantity is set to 0
                    cart.remove(targetProduct);
                    orderService.removeProductFromCart(order, targetProduct);
                } else {
                    // Update the quantity in the cart
                    cart.put(targetProduct, quantity);
                }
                break;
            }
        }

        if (targetProduct != null) {
            OrderProduct orderProduct = orderProductRepository.findOrderProductByOrderIdAndProductId(order.getId(), targetProduct.getId());
            if (orderProduct != null) {
                if (quantity == 0) {
                    orderProductRepository.delete(orderProduct); // Remove the OrderProduct entity if the quantity is 0
                } else {
                    orderProduct.setQuantity(quantity); // Update the quantity in the OrderProduct entity
                    orderProductRepository.save(orderProduct);
                }
            }
        }

        session.setAttribute("productMap", cart);

        // Recalculate total value and total payment
        BigDecimal totalVal = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            totalVal = totalVal.add(entry.getKey().getPrice().multiply(new BigDecimal(entry.getValue())));
        }

        BigDecimal totalPayment = totalVal.add(orderDetailsRepository.findByOrderId(order.getId()).getDeliverPayment());
        session.setAttribute("totalPayment", priceFormattingService.getFormattedPrice(totalPayment));
        session.setAttribute("totalValue", priceFormattingService.getFormattedPrice(totalVal));

        log.debug("Total value: " + totalVal);
        return ResponseEntity.ok("{\"status\":\"success\"}");
    }
    @GetMapping("/confirmOrder")
    public String goConfirmPage(HttpSession session,
                                ModelMap map) {
        log.debug("GET /confirmOrder");
        if (findCurrentOrder(session).getOrderProducts().isEmpty()) {
            return "redirect:/shopping_cart";
        }
        Map<Product, Integer> productMap = (Map<Product, Integer>) session.getAttribute("productMap");
        for (Map.Entry<Product, Integer> entry : productMap.entrySet()) {
            int availableQuanity = productRepository.countProductByNameAndPrice(entry.getKey().getName(), entry.getKey().getPrice());
            log.debug(String.valueOf(availableQuanity));
            if (availableQuanity < entry.getValue()) {
                // Todo : Make notification here
                return "product_cart";
            }
        }
        Account account = accountRepository.findByUsername((String) session.getAttribute("username"));
        AccountDetails accountDetails = accountDetailsRepository.findByAccountId(account.getId());
        OrderDetails orderDetails = orderDetailsRepository.findByOrderId(findCurrentOrder(session).getId());
        map.put("accountDetails", accountDetails);
        map.put("orderDetails", orderDetails);
        session.setAttribute("deliverPayment", priceFormattingService.getFormattedPrice(orderDetails.getDeliverPayment()));
        return "confirm_order";
    }
    @GetMapping("/checkout")
    public String checkout(HttpSession session) {
        log.debug("Checkout");
        Order order = (Order) session.getAttribute("order");
        Map<Product, Integer> productMap = (Map<Product, Integer>) session.getAttribute("productMap");
        for (Map.Entry<Product, Integer> entry : productMap.entrySet()) {
            List<Product> productList = productRepository.getProductsByNameAndPrice(entry.getKey().getName(), entry.getKey().getPrice());
            Product product = entry.getKey();
            productList.remove(product);
            product.setPurchased(true);
            productRepository.save(product);
            for (int i = 0; i < entry.getValue() - 1; i++) {
                productList.get(i).setPurchased(true);
                productRepository.save(productList.get(i));
            }
        }
        order.setCompleted(true);
        OrderDetails orderDetails = orderDetailsRepository.findByOrderId(order.getId());


        orderRepository.save(order);
        String state = String.valueOf(OrderState.PendingConfirmation);
        orderDetails.setState(state);
        orderDetailsRepository.save(orderDetails);
        log.debug(orderDetails.getState());
        log.debug(orderDetails.getState());
        List<Map<Product, Integer>> pendingList = getListProductMapByState(String.valueOf(OrderState.PendingConfirmation));
        List<Map<Product, Integer>> processingList = getListProductMapByState(String.valueOf(OrderState.Processing));
        List<Map<Product, Integer>> InTransitList = getListProductMapByState(String.valueOf(OrderState.InTransit));
        List<Map<Product, Integer>> completedList = getListProductMapByState(String.valueOf(OrderState.Completed));
        List<Map<Product, Integer>> canceledList = getListProductMapByState(String.valueOf(OrderState.Canceled));
        session.setAttribute("pendingList", pendingList);
        session.setAttribute("processingList", processingList);
        session.setAttribute("InTransitList", InTransitList);
        session.setAttribute("completedList", completedList);
        session.setAttribute("canceledList", canceledList);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String goOrdersPage(HttpSession session) {
        log.debug("GET /orders");
        List<Map<Product, Integer>> pendingList = getListProductMapByState(String.valueOf(OrderState.PendingConfirmation));
        List<Map<Product, Integer>> processingList = getListProductMapByState(String.valueOf(OrderState.Processing));
        List<Map<Product, Integer>> InTransitList = getListProductMapByState(String.valueOf(OrderState.InTransit));
        List<Map<Product, Integer>> completedList = getListProductMapByState(String.valueOf(OrderState.Completed));
        List<Map<Product, Integer>> canceledList = getListProductMapByState(String.valueOf(OrderState.Canceled));
        session.setAttribute("pendingList", pendingList);
        session.setAttribute("processingList", processingList);
        session.setAttribute("InTransitList", InTransitList);
        session.setAttribute("completedList", completedList);
        session.setAttribute("canceledList", canceledList);
        return "orders";
    }


    private Order findCurrentOrder(HttpSession session) {
        String username = (String) session.getAttribute("username");
        Account account = accountRepository.findByUsername(username);
        Order order = orderRepository.findByAccountAndCompletedFalse(account);
        if (order == null) {
            order = orderService.createOrder(account);
            session.setAttribute("order", order);
        }
        return order;
    }

    private List<Map<Product, Integer>> getListProductMapByState(String state) {
        List<Map<Product, Integer>> mapList = new ArrayList<>();
        List<OrderDetails> orderDetailsList = orderDetailsRepository.getOrderDetailsByState(state);
        for (OrderDetails orderDetails : orderDetailsList) {
            Order order = orderRepository.findByOrderDetails(orderDetails);
            if (orderService.getProductListOfAnOrder(order).isEmpty()) {
                continue;
            }
            mapList.add(orderProductService.getProductMap(order));
        }
        return mapList;
    }
}
