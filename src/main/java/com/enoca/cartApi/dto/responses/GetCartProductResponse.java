package com.enoca.cartApi.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCartProductResponse {
    private String productName;
    private Long quantity;
}
