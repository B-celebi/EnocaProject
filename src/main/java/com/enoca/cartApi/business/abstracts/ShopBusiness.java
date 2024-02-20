package com.enoca.cartApi.business.abstracts;

import com.enoca.cartApi.dto.requests.AddProductToCartRequest;
import com.enoca.cartApi.dto.requests.RemoveProductFromCartRequest;
import org.springframework.http.ResponseEntity;

public interface ShopBusiness {
    public ResponseEntity<Object> addProductToCart(AddProductToCartRequest addProductToCartRequest);
    public ResponseEntity<Object> removeProductFromCart(RemoveProductFromCartRequest removeProductFromCartRequest);

    public ResponseEntity<Object> placeOrder(String id);
}
