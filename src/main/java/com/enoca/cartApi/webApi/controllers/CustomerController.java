package com.enoca.cartApi.webApi.controllers;

import com.enoca.cartApi.business.abstracts.CustomerBusiness;
import com.enoca.cartApi.dto.requests.CreateCustomerRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@AllArgsConstructor
@NoArgsConstructor
public class CustomerController {
    @Autowired
    private CustomerBusiness customerBusiness;

    @PostMapping("/addCustomer")
    @ResponseStatus(code=HttpStatus.CREATED)
    private ResponseEntity<?> addCustomer(@Valid @RequestBody CreateCustomerRequest createCustomerRequest){
        return this.customerBusiness.addCustomer(createCustomerRequest);
    }

}
