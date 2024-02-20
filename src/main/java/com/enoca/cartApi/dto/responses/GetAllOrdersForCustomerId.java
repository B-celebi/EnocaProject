package com.enoca.cartApi.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllOrdersForCustomerId {
    private String customerId;
    private List<GetProductResponseForOrder> products;
    private Double totalPrice;
}
