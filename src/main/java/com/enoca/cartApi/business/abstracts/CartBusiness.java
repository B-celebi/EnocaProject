package com.enoca.cartApi.business.abstracts;

import org.springframework.http.ResponseEntity;

public interface CartBusiness {
    ResponseEntity<Object> getCartById(String id);
    ResponseEntity<Object> emptyCart(String id,boolean isOrdered);
}
