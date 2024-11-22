package com.project.shopapp.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Data //toString
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    @Min(value = 0, message = "Quantity must be > 0")
    private int quantity;

    @Min(value = 0, message = "Product id must be > 0")
    private Long productId;
}
