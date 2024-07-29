package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.models.Product;
import com.trainings.shoppingcartdemo.repositories.ProductRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class ProductControllerJPA {

    private final ProductRepository productRepository;

    public ProductControllerJPA(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("showProduct")
    public String goShowProductPage(HttpSession session,
                                    @RequestParam("category") String category,
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
        log.debug("Product list in map: " + productList);
        return "showProduct";
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
        log.debug("Product list in session after refresh: " + productList);

        return "redirect:/showProduct?category=" + category;
    }

    @GetMapping("updateProduct")
    public String goUpdateProductPage(@RequestParam("id") Long id, ModelMap map) {
        Product product = productRepository.findById(id).orElse(null);
        map.put("product", product);
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
        productRepository.save(product);
        String category = session.getAttribute("category").toString();
        map.put("productList", productRepository.findByCategory(category));
        return "redirect:/showProduct?category=" + category;
    }
}
