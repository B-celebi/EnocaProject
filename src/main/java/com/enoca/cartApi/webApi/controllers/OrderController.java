package com.enoca.cartApi.webApi.controllers;

import com.enoca.cartApi.business.abstracts.OrderBusiness;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
@NoArgsConstructor
public class OrderController {
    @Autowired
    private OrderBusiness orderBusiness;

    @GetMapping("/getAllOrdersForCustomer/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    private ResponseEntity<Object> getAllOrdersForCustomer(@Valid @Min(36) @Max(36) @PathVariable String id){
        return this.orderBusiness.GetAllOrdersForCustomer(id);
    }
    @GetMapping("/getOrderForCode")
    @ResponseStatus(code=HttpStatus.OK)
    private ResponseEntity<Object> getOrderForCode(@RequestParam String code){
        return this.orderBusiness.getOrderForCode(code);
    }
}
