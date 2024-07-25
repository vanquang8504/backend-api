package com.example.shopapp.response;

import com.example.shopapp.models.OrderDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OrderDetailResponse {
    private long id;

    @JsonProperty("order_id")
    private long orderId;

    @JsonProperty("product_id")
    private long productId;

    private float price;

    @JsonProperty("number_of_product")
    private int numberOfProduct;

    @JsonProperty("total_money")
    private float totalMoney;

    private String color;

    public static OrderDetailResponse formOrderDetailResponse(OrderDetail orderDetail){
        return OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .price(orderDetail.getPrice())
                .productId(orderDetail.getProduct().getId())
                .color(orderDetail.getColor())
                .numberOfProduct(orderDetail.getNumberOfProducts())
                .totalMoney(orderDetail.getTotalMoney())
                .build();
    }
}
