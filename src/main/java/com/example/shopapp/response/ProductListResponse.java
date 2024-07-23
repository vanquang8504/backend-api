package com.example.shopapp.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductListResponse {
    private List<ProductResponse> productResponseList;
    private int totalPage;
}
