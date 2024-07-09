package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.model.Product;
import com.trainings.shoppingcartdemo.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes("productList")
@Controller
public class ShowController {
    private ProductService service;

    public ShowController(ProductService service) {
        this.service = service;
    }

    @RequestMapping(value = "showProduct", method = RequestMethod.GET)
    public String goShowProductPage(HttpSession session) {
        session.setAttribute("productList", service.getProductList());
        return "showProduct";
    }
}
