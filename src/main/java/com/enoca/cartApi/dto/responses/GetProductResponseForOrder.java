package com.enoca.cartApi.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetProductResponseForOrder {
    private String name;
    private Double price;
    private Long quantity;
}
