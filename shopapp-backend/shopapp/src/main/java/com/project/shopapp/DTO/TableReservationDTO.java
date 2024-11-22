package com.project.shopapp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TableReservationDTO {
    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("guest_count")
    private int guestCount;

    @JsonProperty("reservation_time")
    private LocalDateTime reservationTime;

    private Long customerId;

}
