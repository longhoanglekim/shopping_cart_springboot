package com.trainings.shoppingcartdemo.models;

import com.trainings.shoppingcartdemo.enums.OrderState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity(name = "orderdetails")
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private BigDecimal deliverPayment = new BigDecimal(20000);

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;
    private String state = String.valueOf(OrderState.PendingConfirmation);
}
