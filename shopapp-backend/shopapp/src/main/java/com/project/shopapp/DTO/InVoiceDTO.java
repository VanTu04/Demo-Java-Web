package com.project.shopapp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InVoiceDTO {

    private Long orderId;

    @JsonProperty("payment_method")
    private String paymentMethod;
}
