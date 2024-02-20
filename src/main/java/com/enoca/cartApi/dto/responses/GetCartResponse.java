package com.enoca.cartApi.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCartResponse {
    private String customerName;
    private Double totalPrice;
    private List<GetCartProductResponse> getCartProductResponses;
}
