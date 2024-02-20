package com.enoca.cartApi.business.abstracts;

import com.enoca.cartApi.dto.requests.CreateCustomerRequest;
import org.springframework.http.ResponseEntity;

public interface CustomerBusiness {
    ResponseEntity<?> addCustomer(CreateCustomerRequest createCustomerRequest);
}
