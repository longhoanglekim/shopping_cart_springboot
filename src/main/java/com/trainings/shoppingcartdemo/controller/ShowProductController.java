package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@SessionAttributes("productList")
@Controller
public class ShowProductController {
    private ProductService service;

    public ShowProductController(ProductService service) {
        this.service = service;
    }

    // RequestParam is used to get the value of the query string parameter. Example the value after ? in the URL
    @RequestMapping(value = "showProduct", method = RequestMethod.GET)
    public String goShowProductPage(HttpSession session,
                                    @RequestParam("category") String category) {
        System.out.println("Process " + category);
        session.setAttribute("productList", service.getProductList());
        return "showProduct";
    }

}
