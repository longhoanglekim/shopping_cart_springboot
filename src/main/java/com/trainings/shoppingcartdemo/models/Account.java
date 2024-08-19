package com.trainings.shoppingcartdemo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
    @Column(name = "cash_in_wallet", nullable = false)
    private BigDecimal cashInWallet = BigDecimal.ZERO;

    @OneToMany(mappedBy = "account", orphanRemoval = false)
    private List<Product> productList;

    @OneToMany(mappedBy = "account")
    private List<Order> orderList;
    public String getFormattedCash() {
        // Ensure cashInWallet is not null
        if (cashInWallet == null) {
            return "0.00";
        }

        // Kiểm tra phần thập phân
        BigDecimal integerPart = cashInWallet.setScale(0, BigDecimal.ROUND_DOWN);
        BigDecimal fractionalPart =cashInWallet.subtract(integerPart);

        DecimalFormat decimalFormat;
        if (fractionalPart.equals(BigDecimal.ZERO)) {
            decimalFormat = new DecimalFormat("#,###");
        } else {
            decimalFormat = new DecimalFormat("#,###.##");
        }

        return decimalFormat.format(cashInWallet);
    }
}
