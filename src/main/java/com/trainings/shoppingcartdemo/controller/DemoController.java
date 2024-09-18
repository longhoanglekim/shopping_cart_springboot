package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.dto.MessageDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo-api")
public class DemoController {
    @GetMapping("/hello")
    public MessageDto hello() {
        return new MessageDto("Hello, World!, This is a demo API");
    }
}
