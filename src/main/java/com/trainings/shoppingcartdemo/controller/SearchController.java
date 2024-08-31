package com.trainings.shoppingcartdemo.controller;

import com.trainings.shoppingcartdemo.models.Account;
import com.trainings.shoppingcartdemo.models.Product;
import com.trainings.shoppingcartdemo.repositories.AccountRepository;
import com.trainings.shoppingcartdemo.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("")
public class SearchController {
    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;

    public SearchController(AccountRepository accountRepository, ProductRepository productRepository) {
        this.accountRepository = accountRepository;
        this.productRepository = productRepository;
    }
    @ResponseBody
    @GetMapping("/api/search/searchSuggestions")
    List<String> searchItem(@RequestParam("keyword") String keyword) {
        List<String> findList = new ArrayList<>();
        log.debug("API SEARCH keyword : " + keyword);
        if (productRepository.search(keyword) != null) {
            log.debug("Exist products");
        }
        for (Product p : productRepository.search(keyword)) {
            findList.add(p.getName());
        }
        for (Account a : accountRepository.search(keyword)) {
            findList.add(a.getUsername());
        }
       for (String s : findList) {
           log.debug(s);
       }
        return findList;
    }

    @GetMapping("/search")
    public String getSearchList(@RequestParam("selectedItems") String selectedItems,
                                @RequestParam("keyword") String keyword,
                                ModelMap map) {
        log.debug("POST /search");
        String[] items = selectedItems.split(",");

        for (String item : items) {
            log.debug("Selected item: " + item);
        }
        map.put("keyword", keyword);
        map.put("bestShop", accountRepository.search(keyword).get(0));
        map.put("productList", productRepository.search(keyword));
        return "search";
    }

}
