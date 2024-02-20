package com.enoca.cartApi.webApi.controllers;

import com.enoca.cartApi.business.abstracts.ProductBusiness;
import com.enoca.cartApi.common.responseHandlers.ResponseHandler;
import com.enoca.cartApi.dto.requests.CreateProductRequest;
import com.enoca.cartApi.dto.requests.UpdateProductRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
@NoArgsConstructor
public class ProductController {
    @Autowired
    private ProductBusiness productBusiness;
    @GetMapping("/getProductById/{id}")
    @ResponseStatus(code=HttpStatus.OK)
    private ResponseEntity<Object> getProductById(@Valid @PathVariable @Size(min=36,max=36) String id){
        return this.productBusiness.getProductById(id);
    }
    @PostMapping("/add")
    @ResponseStatus(code= HttpStatus.CREATED)
    private ResponseEntity<Object> addProduct(@RequestBody CreateProductRequest createProductRequest){
        return this.productBusiness.addProduct(createProductRequest);
    }
    @PutMapping("/update")
    @ResponseStatus(code=HttpStatus.OK)
    private ResponseEntity<Object> updateProduct(@RequestBody UpdateProductRequest updateProductRequest){
    return this.productBusiness.updateProduct(updateProductRequest);
    }
    @DeleteMapping("/delete/{id}")
    private ResponseEntity<Object> deleteProduct(@Valid @PathVariable @Size(min=36,max=36) String id){
        return this.productBusiness.deleteProduct(id);
    }

}
