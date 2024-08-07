package com.trainings.shoppingcartdemo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Controller
public class WelcomeController {

    @RequestMapping(value = "welcome", method = RequestMethod.GET)
    public String goWelcomePage(HttpSession session) {
        // System.out.println(session.getAttribute("username"));
        session.setAttribute("username", session.getAttribute("username"));
        return "welcome-user";
    }


}
