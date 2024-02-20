package com.enoca.cartApi.dao;

import com.enoca.cartApi.entities.concretes.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDao extends JpaRepository<Cart,String> {
}
