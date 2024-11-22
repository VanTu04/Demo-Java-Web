package com.project.shopapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.shopapp.enums.RESERVATION_STATUS;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
@Entity
@Getter
@Setter
@Builder
@Table(name = "table_reservation")
@AllArgsConstructor
@NoArgsConstructor
public class TableReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "guest_count")
    private int guestCount;

    @Enumerated(EnumType.STRING)
    private RESERVATION_STATUS status;

    @Column(name = "reservation_time")
    private String reservationTime;

    @Column(name = "reservation_code", nullable = false, unique = true)
    private String reservationCode;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "customer_id")
    private User customer;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;
}
