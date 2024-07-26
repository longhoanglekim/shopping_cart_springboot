package com.trainings.shoppingcartdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@SessionAttributes("username")
@Controller
public class LoginController {

    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String goLoginPage() {
        return "login";
    }


}