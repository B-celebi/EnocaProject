package com.enoca.cartApi.business.abstracts;

import com.enoca.cartApi.dto.requests.CreateProductRequest;
import com.enoca.cartApi.dto.requests.UpdateProductRequest;
import com.enoca.cartApi.dto.responses.GetProductResponse;
import org.springframework.http.ResponseEntity;

public interface ProductBusiness {
    ResponseEntity<Object> getProductById(String id);
    ResponseEntity<Object> addProduct(CreateProductRequest createProductRequest);
    ResponseEntity<Object> updateProduct(UpdateProductRequest updateProductRequest);
    ResponseEntity<Object> deleteProduct(String id);
}
