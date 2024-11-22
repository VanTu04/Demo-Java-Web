package com.project.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;
@Data //toString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductsResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private String thumbnail;
    private String description;
    private boolean available;
    @JsonProperty("category_id")
    private Long categoryId;
}
