package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.repositories.ProductRepository;
import com.trainings.shoppingcartdemo.services.AccountService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
@Controller
@Slf4j
public class WelcomeController {
    private final AccountService accountService;
    private final ProductAPIController productAPIController;
    public WelcomeController(AccountService accountService, ProductAPIController productAPIController) {
        this.accountService = accountService;
        this.productAPIController = productAPIController;
    }

    @RequestMapping(value = "welcome", method = RequestMethod.GET)
    public String goWelcomePage(HttpSession session, ModelMap map) {
        // System.out.println(session.getAttribute("username"));
        if (accountService.getCurrentAccount() != null) {
            session.setAttribute("username", accountService.getCurrentAccount().getUsername());
        }
        map.put("categories", productAPIController.getAllCategories());
        log.debug("Get welcome");
        return "welcome_user";
    }


}
