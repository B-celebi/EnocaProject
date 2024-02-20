package com.enoca.cartApi.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductToCartRequest {
    private String cartId;
    private String productId;
    private Long quantity;
}
