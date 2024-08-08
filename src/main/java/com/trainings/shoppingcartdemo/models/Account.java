package com.trainings.shoppingcartdemo.models;

import jakarta.persistence.*;
import javassist.bytecode.annotation.LongMemberValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @OneToMany(mappedBy = "account", orphanRemoval = false)
    private List<Product> productList;

    @OneToMany(mappedBy = "account")
    private List<Order> orderList;
}
