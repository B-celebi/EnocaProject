package com.enoca.cartApi.webApi.controllers;

import com.enoca.cartApi.business.abstracts.CartBusiness;
import com.enoca.cartApi.business.abstracts.ShopBusiness;
import com.enoca.cartApi.dto.requests.AddProductToCartRequest;
import com.enoca.cartApi.dto.requests.RemoveProductFromCartRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shopApi")
@AllArgsConstructor
@NoArgsConstructor
public class ShopController {
    @Autowired
    private ShopBusiness shopBusiness;
    @Autowired
    private CartBusiness cartBusiness;
    @PostMapping("/addProductToCart")
    @ResponseStatus(HttpStatus.OK)
    private ResponseEntity<Object> addProductToCart(@Valid @RequestBody AddProductToCartRequest addProductToCartRequest){
        return this.shopBusiness.addProductToCart(addProductToCartRequest);
    };
    @PostMapping("/removeProductFromCart")
    @ResponseStatus(HttpStatus.OK)
    private ResponseEntity<Object> removeProductFromCart(@Valid @RequestBody RemoveProductFromCartRequest removeProductFromCartRequest){
        return this.shopBusiness.removeProductFromCart(removeProductFromCartRequest);
    };
    @PostMapping("/placeOrder")
    @ResponseStatus(HttpStatus.CREATED)
    private ResponseEntity<Object> placeOrder(@RequestParam String cartId){
        return this.shopBusiness.placeOrder(cartId);
    }
    @DeleteMapping("/emptyCart/{id}")
    @ResponseStatus(HttpStatus.OK)
    private ResponseEntity<Object> emptyCart(@PathVariable String id){
        return this.cartBusiness.emptyCart(id,false);
    }
}
