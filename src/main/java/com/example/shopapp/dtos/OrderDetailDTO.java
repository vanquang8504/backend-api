package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailDTO {
    @JsonProperty("order_id")
    @Min(value = 0, message = "Order's ID must be > 0")
    private long orderId;

    @JsonProperty("product_id")
    @Min(value = 0, message = "Product's ID must be > 0")
    private long productId;

    private float price;

    @JsonProperty("number_of_product")
    @Min(value = 1, message = "Number of product must be > 0")
    private int numberOfProduct;

    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be >= 0")
    private float totalMoney;

    private String color;
}
