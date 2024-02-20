package com.enoca.cartApi.dto.responses;

import com.enoca.cartApi.entities.concretes.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCustomerResponse {
    private String name;
    private String surname;
    private String cartId;
    private List<GetAllOrdersForCustomerId> getAllOrdersForCustomerIds;
}
