package com.enoca.cartApi.business.abstracts;

import org.springframework.http.ResponseEntity;

public interface OrderBusiness {
    ResponseEntity<Object> GetAllOrdersForCustomer(String id);
    String generateOrderCode(String productName,Long quantity,String customerName);
    ResponseEntity<Object> getOrderForCode(String code);
}
