package com.enoca.cartApi.business.concretes;

import com.enoca.cartApi.business.abstracts.OrderBusiness;
import com.enoca.cartApi.business.abstracts.ShopBusiness;
import com.enoca.cartApi.business.rules.ShopManagerRules;
import com.enoca.cartApi.common.exceptions.BusinessException;
import com.enoca.cartApi.common.responseHandlers.ResponseHandler;
import com.enoca.cartApi.common.utilities.abstracts.ModelMapperBusiness;
import com.enoca.cartApi.dao.*;
import com.enoca.cartApi.dto.requests.AddProductToCartRequest;
import com.enoca.cartApi.dto.requests.RemoveProductFromCartRequest;
import com.enoca.cartApi.dto.responses.GetCartResponse;
import com.enoca.cartApi.entities.concretes.Cart;
import com.enoca.cartApi.entities.concretes.CartProduct;
import com.enoca.cartApi.entities.concretes.Order;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ShopManager implements ShopBusiness {
    @Autowired
    private CartDao cartDao;
    @Autowired
    private OrderBusiness orderBusiness;
    @Autowired
    private CartManager cartManager;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CartProductDao cartProductDao;
    @Autowired
    private ModelMapperBusiness modelMapperBusiness;
    @Autowired
    private ShopManagerRules shopManagerRules;

    Logger logger = LoggerFactory.getLogger(ShopManager.class);
    public ResponseEntity<Object> addProductToCart(AddProductToCartRequest addProductToCartRequest){
        //checking stocks
        Cart cart = this.cartDao.findById(addProductToCartRequest.getCartId()).orElseThrow(() -> new BusinessException("No carts found with this id."));
        this.shopManagerRules.checkIfStockDecent(addProductToCartRequest.getProductId(), addProductToCartRequest.getQuantity());
        this.cartManager.addProduct(addProductToCartRequest);
        GetCartResponse getCartResponse = this.modelMapperBusiness.forResponse().map(cart, GetCartResponse.class);
        return ResponseHandler.getSuccessResponse(HttpStatus.OK,getCartResponse);
    }

    @Override
    public ResponseEntity<Object> removeProductFromCart(RemoveProductFromCartRequest removeProductFromCartRequest) {
        Cart cart = this.cartDao.findById(removeProductFromCartRequest.getCartId()).orElseThrow(() -> new BusinessException("No carts found with this id."));
        //checking if cart contains product
        this.shopManagerRules.checkIfCartContainsProduct(removeProductFromCartRequest);
        this.cartManager.removeProduct(removeProductFromCartRequest);
        GetCartResponse getCartResponse = this.modelMapperBusiness.forResponse().map(cart, GetCartResponse.class);
        return ResponseHandler.getSuccessResponse(HttpStatus.OK,getCartResponse);
    }
    @Override
    public ResponseEntity<Object> placeOrder(String cartId) {
        Cart cart = this.cartDao.findById(cartId).orElseThrow(() -> new BusinessException("No carts found with this id."));
        Set<CartProduct> cartProducts = this.cartProductDao.findCartProductsByProductCartId(cartId);
        if(cartProducts.isEmpty()){
            throw new BusinessException("No items in your cart");
        }
        for(CartProduct cp : cartProducts){
            Order order =  new Order();
            order.setId(UUID.randomUUID().toString());
            order.setCode(this.orderBusiness.generateOrderCode(cp.getProduct().getName(),cp.getQuantity(),cp.getProductCart().getCustomer().getName()));
            order.setCustomer(cp.getProductCart().getCustomer());
            order.setProduct(cp.getProduct());
            order.setTotalPrice((cp.getProduct().getPrice())*cp.getQuantity().doubleValue());
            order.setQuantity(cp.getQuantity());
            order.setDate(LocalDateTime.now());
            this.orderDao.save(order);
        }
        this.cartManager.emptyCart(cartId,true);
        logger.info("Order has been given successfully.");
        return ResponseHandler.getSuccessResponse(HttpStatus.CREATED,"Order has been given.");
    }
}
