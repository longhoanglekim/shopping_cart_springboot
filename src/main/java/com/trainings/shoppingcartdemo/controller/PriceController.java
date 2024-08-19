package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.services.PriceFormattingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class PriceController {

    @Autowired
    private PriceFormattingService priceFormattingService;

    @GetMapping("/api/formatPrice")
    public String formatPrice(@RequestParam("price") BigDecimal price) {
        return priceFormattingService.getFormattedPrice(price);
    }
}
