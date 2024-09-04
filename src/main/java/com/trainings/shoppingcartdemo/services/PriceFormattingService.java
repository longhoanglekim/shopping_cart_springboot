package com.trainings.shoppingcartdemo.services;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Service
public class PriceFormattingService {

    public String getFormattedPrice(BigDecimal price) {
        if (price == null) {
            return "0.00";
        }

        BigDecimal integerPart = price.setScale(0, BigDecimal.ROUND_DOWN);
        BigDecimal fractionalPart = price.subtract(integerPart);

        DecimalFormat decimalFormat;
        if (fractionalPart.compareTo(BigDecimal.ZERO) == 0) {
            decimalFormat = new DecimalFormat("#,###");
        } else {
            decimalFormat = new DecimalFormat("#,###.##");
        }

        return decimalFormat.format(price);
    }
}
