package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@Table(name = "invoice")
@AllArgsConstructor
@NoArgsConstructor
public class InVoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Liên kết tới người dùng
    private User user;

    @Column(name = "payment_method")
    private String paymentMethod; // Phương thức thanh toán

    @Column(name = "payment_time")
    private LocalDateTime paymentTime; // Thời gian thanh toán
}
