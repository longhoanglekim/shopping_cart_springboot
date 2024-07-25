package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.model.Product;
import com.trainings.shoppingcartdemo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class AddProductController {
    private ProductService service = new ProductService();

    public AddProductController(ProductService service) {
        this.service = service;
    }

    @RequestMapping(value = "addProduct", method = RequestMethod.GET)
    public String goAddProductPage(ModelMap map) {
        Product product = new Product(0, "", "", "", new BigDecimal(0));
        map.put("product", product);
        return "addProduct";
    }

    @RequestMapping(value = "addProduct", method = RequestMethod.POST)
    public String addProduct(@Valid Product product, BindingResult result) {
        if (result.hasErrors()) {
            return "addProduct";
        }
        service.addProduct(product.getName(), product.getDescription(), product.getCategory(), new BigDecimal(String.valueOf(product.getPrice())));
        return "redirect:/showProduct";
    }
}
