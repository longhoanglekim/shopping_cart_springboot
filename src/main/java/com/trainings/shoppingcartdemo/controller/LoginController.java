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
    public String goWelcomePageAfterLogin(@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                ModelMap map,
                                HttpSession session) {
        StringBuilder message = new StringBuilder();
        if (authentication.checkValidAccount(username, password, message)) {
            map.put("messageLogin", null);
            session.setAttribute("username", username);
            return "welcome";
        }
        map.put("messageLogin", message.toString());
        return "login";
    }
}
