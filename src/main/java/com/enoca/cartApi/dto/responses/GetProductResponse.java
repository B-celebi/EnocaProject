package com.enoca.cartApi.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProductResponse {
    private String name;
    private Double price;
    private Long stock;
}
