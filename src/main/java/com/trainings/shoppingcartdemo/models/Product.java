package com.trainings.shoppingcartdemo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

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
    @NumberFormat(pattern = "#,###.##")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero.")
    @DecimalMax(value = "100000000.0", message = "Price must be less than 100000000.")
    private BigDecimal price;

    public Product(String name, String description, String category, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
