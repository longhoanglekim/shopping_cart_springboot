package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.models.Account;
import com.trainings.shoppingcartdemo.models.Order;
import com.trainings.shoppingcartdemo.models.Product;
import com.trainings.shoppingcartdemo.repositories.AccountRepository;
import com.trainings.shoppingcartdemo.repositories.OrderProductRepository;
import com.trainings.shoppingcartdemo.repositories.OrderRepository;
import com.trainings.shoppingcartdemo.repositories.ProductRepository;
import com.trainings.shoppingcartdemo.services.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class ProductController {

    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderService orderService;

    public ProductController(ProductRepository productRepository, AccountRepository accountRepository, OrderRepository orderRepository, OrderProductRepository orderProductRepository, OrderService orderService) {
        this.productRepository = productRepository;
        this.accountRepository = accountRepository;
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.orderService = orderService;
    }

    @GetMapping("showListProduct/{category}")
    public String goShowlistProductPage(HttpSession session,
                                    //@RequestParam("category") String category,
                                    @PathVariable String category,
                                    ModelMap map) {
        log.debug("GET /showProduct");

        // Store category in session
        session.setAttribute("category", category);

        List<Product> productList;
        if (category.equals("all")) {
            productList = productRepository.findAll();
        } else {
            productList = productRepository.findByCategory(category);
        }
        // Store product list in map
        map.put("productList", productList);
        log.debug("The category is: " + category);
//        log.debug("Product list in map: " + productList);
        return "showListProduct";
    }

    @GetMapping("addProduct")
    public String goAddProductPage(ModelMap map) {
        log.debug("GET /addProduct");
        map.put("product", new Product("", "", "", null));
        return "addProduct";
    }

    @PostMapping("addProduct")
    public String addProduct(@Valid Product product,
                             BindingResult result,
                             ModelMap map,
                             HttpSession session) {
        log.debug("POST /addProduct");

        if (result.hasErrors()) {
            log.error("Error in the form");
            return "addProduct";
        }
        Account account = accountRepository.findByUsername((String) session.getAttribute("username"));
        product.setAccount(account);
        product.setOrder(null);
        log.debug("Product before saving: " + product);
        productRepository.save(product);

        // Refresh the product list in the session
        String category = session.getAttribute("category").toString();
        List<Product> productList;
        if (category.equals("all")) {
            productList = productRepository.findAll();
        } else {
            productList = productRepository.findByCategory(category);
        }

        map.put("productList", productList);
//        log.debug("Product list in session after refresh: " + productList);

        return "redirect:/showListProduct/" + category;
    }

    @GetMapping("updateProduct")
    public String goUpdateProductPage(@RequestParam("id") Long id,
                                      ModelMap map,
                                      HttpSession session) {
        Product product = productRepository.findById(id).orElse(null);
        map.put("product", product);
        session.setAttribute("currentOrder", product.getOrder());
        session.setAttribute("currentAccount", product.getAccount());
        return "updateProduct";
    }

    @PostMapping("updateProduct")
    public String updateProduct(@Valid Product product,
                                BindingResult result,
                                ModelMap map, HttpSession session) {
        log.debug("POST /updateProduct");
        if (result.hasErrors()) {
            return "updateProduct";
        }
        product.setOrder((Order) session.getAttribute("currentOrder"));
        product.setAccount((Account) session.getAttribute("currentAccount"));
        productRepository.save(product);
        String category = session.getAttribute("category").toString();
        map.put("productList", productRepository.findByCategory(category));
        return "redirect:/showListProduct/" + category;
    }

    @GetMapping("productInfo")
    public String showProductInfo(@RequestParam(name = "id") String id,
                                  ModelMap map) {
        Product product = productRepository.findById(Long.parseLong(id)).get();
        map.put("product", product);
        log.debug("Get Show Info");
        return "productInfo";
    }
    @Transactional
    @PostMapping("deleteProduct")
    public ResponseEntity<String> deleteProduct(@RequestParam(name = "id") String id, HttpSession session) {
        log.debug("Delete product with id = " + id);
        Optional<Product> product = productRepository.findById(Long.parseLong(id));
        if (product.isPresent()) {
            Product presentProduct = product.get();
            // Manually delete related records in order_products table
            if (presentProduct.getOrder() != null) {
                orderService.removeProductFromCart(presentProduct.getOrder(), presentProduct);
            }
            orderProductRepository.deleteByProductId(presentProduct.getId());
            productRepository.deleteById(presentProduct.getId());
            return ResponseEntity.ok("Product deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }
}
