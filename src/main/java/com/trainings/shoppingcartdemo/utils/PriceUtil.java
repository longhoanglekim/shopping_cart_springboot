package com.trainings.shoppingcartdemo.utils;

import org.springframework.context.annotation.Bean;

public class PriceUtil {
    public String convertStringPrice(String price) {
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 0; i < price.length(); i++) {
          if (Character.isDigit(price.charAt(i))) {
              stringBuilder.append(price.charAt(i));
          }
      }
      return stringBuilder.toString();
    }
}
