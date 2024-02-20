package com.enoca.cartApi.business.concretes;

import com.enoca.cartApi.business.abstracts.CustomerBusiness;
import com.enoca.cartApi.common.responseHandlers.ResponseHandler;
import com.enoca.cartApi.common.utilities.abstracts.ModelMapperBusiness;
import com.enoca.cartApi.dao.CartDao;
import com.enoca.cartApi.dao.CustomerDao;
import com.enoca.cartApi.dto.requests.CreateCustomerRequest;
import com.enoca.cartApi.dto.responses.GetCustomerResponse;
import com.enoca.cartApi.entities.concretes.Cart;
import com.enoca.cartApi.entities.concretes.Customer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CustomerManager implements CustomerBusiness {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private CartDao cartDao;
    @Autowired
    private ModelMapperBusiness modelMapperBusiness;
    @Override
    public ResponseEntity<?> addCustomer(CreateCustomerRequest createCustomerRequest) {
        Customer customer = this.modelMapperBusiness.forRequest().map(createCustomerRequest, Customer.class);
        customer.setId(UUID.randomUUID().toString());
        Customer saved = this.customerDao.save(customer);

        Cart cart = new Cart();
        cart.setId(UUID.randomUUID().toString());
        cart.setTotalPrice(0.0);
        cart.setCustomer(saved);
        this.cartDao.save(cart);

        saved.setCart(cart);
        this.customerDao.save(saved);

        GetCustomerResponse getCustomerResponse = this.modelMapperBusiness.forResponse().map(saved, GetCustomerResponse.class);

        return ResponseHandler.getSuccessResponse(HttpStatus.CREATED,getCustomerResponse );
    }
}
