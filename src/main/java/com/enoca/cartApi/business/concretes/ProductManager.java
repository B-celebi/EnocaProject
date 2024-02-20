package com.enoca.cartApi.business.concretes;

import com.enoca.cartApi.business.abstracts.ProductBusiness;
import com.enoca.cartApi.common.exceptions.BusinessException;
import com.enoca.cartApi.common.responseHandlers.ResponseHandler;
import com.enoca.cartApi.common.utilities.abstracts.ModelMapperBusiness;
import com.enoca.cartApi.dao.ProductDao;
import com.enoca.cartApi.dto.requests.CreateProductRequest;
import com.enoca.cartApi.dto.requests.UpdateProductRequest;
import com.enoca.cartApi.dto.responses.GetProductResponse;
import com.enoca.cartApi.entities.concretes.Product;
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
public class ProductManager implements ProductBusiness {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ModelMapperBusiness modelMapperBusiness;
    @Override
    public ResponseEntity<Object> getProductById(String id) {
        Product product = this.productDao.findById(id).orElseThrow(()-> new BusinessException("No products found with this id."));
        GetProductResponse getProductResponse = modelMapperBusiness.forResponse().map(product, GetProductResponse.class);
        return ResponseHandler.getSuccessResponse(HttpStatus.OK,getProductResponse);
    }

    @Override
    public ResponseEntity<Object> addProduct(CreateProductRequest createProductRequest) {
        if(createProductRequest.getStock()==0){
            throw new BusinessException("Product can't be add with 0 stocks.");
        }
        Product product = modelMapperBusiness.forRequest().map(createProductRequest,Product.class);
        product.setId(UUID.randomUUID().toString());
        this.productDao.save(product);
        GetProductResponse getProductResponse = modelMapperBusiness.forResponse().map(product, GetProductResponse.class);
        return ResponseHandler.getSuccessResponse(HttpStatus.CREATED,getProductResponse);
    }

    @Override
    public ResponseEntity<Object> updateProduct(UpdateProductRequest updateProductRequest) {
        Product product = this.modelMapperBusiness.forRequest().map(updateProductRequest,Product.class);
        this.productDao.save(product);
        GetProductResponse getProductResponse = modelMapperBusiness.forResponse().map(product, GetProductResponse.class);
        return ResponseHandler.getSuccessResponse(HttpStatus.OK, getProductResponse);
    }

    @Override
    public ResponseEntity<Object> deleteProduct(String id) {
        Product product = this.productDao.findById(id).orElseThrow(() -> new BusinessException("No product found with this id."));
        this.productDao.deleteById(id);
        GetProductResponse getProductResponse = modelMapperBusiness.forResponse().map(product, GetProductResponse.class);
        return ResponseHandler.getSuccessResponse(HttpStatus.OK,getProductResponse);
    }
}
