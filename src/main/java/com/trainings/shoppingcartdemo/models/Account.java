package com.trainings.shoppingcartdemo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "accounts")
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String role;
    private boolean enabled;
    @Column(name = "cash_in_wallet", nullable = false)
    private BigDecimal cashInWallet = BigDecimal.ZERO;

    @OneToMany(mappedBy = "account", orphanRemoval = false)
    @JsonBackReference
    private List<Product> productList;

    @OneToMany(mappedBy = "account")
    private List<Order> orderList;
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private AccountDetails accountDetail;
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public String getFormattedCash() {
        // Ensure cashInWallet is not null
        if (cashInWallet == null) {
            return "0.00 VND";
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
