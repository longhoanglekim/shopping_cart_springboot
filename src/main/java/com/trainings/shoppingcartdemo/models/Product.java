package com.trainings.shoppingcartdemo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, message = "Name must be more than 3 characters.")
    @Size(max = 50, message = "Name must be less than 50 characters.")
    private String name;

    private String description;
    private String category;
    private boolean isPurchased = false;

    @NumberFormat(pattern = "#,###.##")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero.")
    @DecimalMax(value = "100000000.0", message = "Price must be less than 100000000.")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderProduct> orderProducts;

    public Product(String name, String description, String category, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    public String getFormattedPrice() {
        if (price == null) {
            return "0.00 VND";
        }

        BigDecimal integerPart = price.setScale(0, BigDecimal.ROUND_DOWN);
        BigDecimal fractionalPart = price.subtract(integerPart);

        DecimalFormat decimalFormat;
        if (fractionalPart.equals(BigDecimal.ZERO)) {
            decimalFormat = new DecimalFormat("#,###");
        } else {
            decimalFormat = new DecimalFormat("#,###.##");
        }

        return decimalFormat.format(price) + " VND";
    }
}
