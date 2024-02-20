package com.enoca.cartApi.business.rules;

import com.enoca.cartApi.common.exceptions.BusinessException;
import com.enoca.cartApi.dao.CartDao;
import com.enoca.cartApi.dao.ProductDao;
import com.enoca.cartApi.dto.requests.RemoveProductFromCartRequest;
import com.enoca.cartApi.entities.concretes.Cart;
import com.enoca.cartApi.entities.concretes.CartProduct;
import com.enoca.cartApi.entities.concretes.Product;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ShopManagerRules {
    @Autowired
    private CartDao cartDao;
    @Autowired
    private ProductDao productDao;
    public void checkIfStockDecent(String id,Long quantity){
        if(productDao.findById(id).orElseThrow(()-> new BusinessException("No products found with this id.")).getStock() - quantity < 0){
            throw new BusinessException("Not enough stocks.");
        }
    }
    public void checkIfCartContainsProduct(RemoveProductFromCartRequest removeProductFromCartRequest){
        Product product = this.productDao.findById(removeProductFromCartRequest.getProductId()).orElseThrow(()-> new BusinessException("No products found with this id."));
        Cart cart = this.cartDao.findById(removeProductFromCartRequest.getCartId()).orElseThrow(()-> new BusinessException("No carts found with this id."));
        /*if(!cart.getProducts().stream().anyMatch(p -> p.getId().equals(product.getId()))){
            throw new BusinessException("This Cart doesn't contain the Product you tried to remove.");
        }
        */
        Set<CartProduct> cartProducts = cart.getCartProducts();
        //görmüyor
        boolean isExists = false;
        for(CartProduct cartProduct : cartProducts){
            if(cartProduct.getProduct().getId().equals(removeProductFromCartRequest.getProductId())){
                isExists = true;
                break;
            }
        }
        if(!isExists){
            throw new BusinessException("This Cart doesn't contain the Product you tried to remove.");
        }
    }
}
