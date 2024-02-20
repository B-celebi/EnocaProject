package com.enoca.cartApi.webApi.controllers;

import com.enoca.cartApi.business.abstracts.CartBusiness;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
@NoArgsConstructor
public class CartController {
    @Autowired
    private CartBusiness cartBusiness;

    @GetMapping("/getById/{id}")
    @ResponseStatus(code=HttpStatus.OK)
    private ResponseEntity<Object> getById(@Valid @PathVariable @Size(min=36,max=36) String id){
        return this.cartBusiness.getCartById(id);
    }
}
