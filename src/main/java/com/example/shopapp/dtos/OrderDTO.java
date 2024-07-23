package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDTO {
    @JsonProperty("user_id")
    @Min(value = 0, message = "User's ID must be > 0")
    private long userId;

    @JsonProperty("fullname")
    private String fullName;


    private String email;

    @NotBlank(message = "Phone number is required")
    @JsonProperty("phone_number")
    private String phoneNumber;

    private String address;

    private String note;

    @Min(value = 0, message = "Total money must be >= 0")
    @JsonProperty("total_money")
    private float totalMoney;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    private String status;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("shipping_date")
    private LocalDate shippingDate;

    @JsonProperty("payment_method")
    private String paymentMethod;

    private Boolean active;
}
