package com.trainings.shoppingcartdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WelcomeController {
    @RequestMapping(value = "welcome", method = RequestMethod.GET)
    public String goWelcomePage() {
        return "welcome";
    }


}
