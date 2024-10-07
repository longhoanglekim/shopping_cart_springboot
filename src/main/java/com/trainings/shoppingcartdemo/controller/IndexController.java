package com.trainings.shoppingcartdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@Slf4j
public class IndexController implements ErrorController{
    private final static String PATH = "/error";

    @RequestMapping(PATH)
    @ResponseBody
    public String getErrorPath() {
        // TODO Auto-generated method stub
        // log the detail error
        log.error("Error Path");
        return "No Mapping Found";
    }

}