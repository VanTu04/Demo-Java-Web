package com.project.shopapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.shopapp.enums.PAYMENT_STATUS;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_time")
    private String orderTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PAYMENT_STATUS paymentStatus;

    @Column(name = "payment_time")
    private String paymentTime;

    private BigDecimal totalPrice;

//    @ManyToOne
//    @JoinColumn(name = "customer_id")
//    private User customer;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "reservation_id", nullable = false)
    private TableReservation reservation;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<OrderItem> orderItems;

}
