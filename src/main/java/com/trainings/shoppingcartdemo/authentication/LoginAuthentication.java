package com.trainings.shoppingcartdemo.authentication;

import org.springframework.stereotype.Service;

@Service
public class LoginAuthentication {
    //Todo Add logic validation with database after
    public boolean checkValidAccount(String username, String password, StringBuilder message) {
        if (username.equals("hlklonga5") && password.equals("12345678")) {
            message = null;
            return true;
        }
        message.append("Wrong ID");
        return false;
    }
}
