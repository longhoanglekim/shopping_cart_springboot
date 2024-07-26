package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.models.Product;
import com.trainings.shoppingcartdemo.daos.ProductDao;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@SessionAttributes("productList")
@Controller
public class ProductController {
    private final ProductDao service;

    public ProductController(ProductDao service) {
        this.service = service;
    }

    // RequestParam is used to get the value of the query string parameter. Example the value after ? in the URL
    @RequestMapping(value = "showProduct", method = RequestMethod.GET)
    public String goShowProductPage(HttpSession session,
                                    @RequestParam("category") String category) {
        // System.out.println("Process " + category);
        session.setAttribute("category", category);
        session.setAttribute("productList", service.getProductList());
        return "showProduct";
    }

    @RequestMapping(value = "addProduct", method = RequestMethod.GET)
    public String goAddProductPage(ModelMap map) {
        Product product = new Product(0, "", "", "", null);
        map.put("product", product);
        return "addProduct";
    }

    @RequestMapping(value = "addProduct", method = RequestMethod.POST)
    public String addProduct(@Valid Product product, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "addProduct";
        }
        service.addProduct(product.getName(), product.getDescription(), product.getCategory(), new BigDecimal(String.valueOf(product.getPrice())));
        return "redirect:/showProduct?category=" + session.getAttribute("category");
    }

    @RequestMapping(value = "updateProduct", method = RequestMethod.GET)
    public String goUpdateProductPage(@RequestParam("id") Integer id, ModelMap map) {
        Product product = service.getProductById(id);
        map.put("product", product);
        return "updateProduct";
    }

    @RequestMapping(value = "updateProduct", method = RequestMethod.POST)
    public String updateProduct(@Valid Product product, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "updateProduct";
        }
        service.updateProduct(product.getId(), product.getName(), product.getDescription(), product.getCategory(), new BigDecimal(String.valueOf(product.getPrice())));
        return "redirect:/showProduct?category=" + session.getAttribute("category");
    }
}
