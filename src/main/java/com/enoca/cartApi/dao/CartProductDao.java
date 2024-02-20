package com.enoca.cartApi.dao;

import com.enoca.cartApi.entities.concretes.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CartProductDao extends JpaRepository<CartProduct,String> {
    Set<CartProduct> getCartProductsByProductCartId(String id);
    CartProduct findCartProductByProductCartIdAndProductId(String cartId,String productId);

    Set<CartProduct> findCartProductsByProductCartId(String productCartId);
    void deleteCartProductsByProductCartId(String id);
}
