package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.authentication.LoginAuthentication;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@SessionAttributes("username")
@Controller
public class LoginController {
    private LoginAuthentication authentication;
    LoginController(LoginAuthentication authentication) {
        this.authentication = authentication;
    }
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String goLoginPage() {
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String goWelcomePage(@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                ModelMap map,
                                HttpSession session) {
        if (authentication.checkValidAccount(username, password)) {
            map.put("messageLogin", null);
            session.setAttribute("username", username);
            return "welcome";
        }
        map.put("messageLogin", "Wrong ID");
        return "login";
    }


    @RequestMapping(value = "welcome", method = RequestMethod.GET)
    public String goWelcomePage() {
        return "welcome";
    }
}
