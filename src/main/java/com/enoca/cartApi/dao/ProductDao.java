package com.enoca.cartApi.dao;

import com.enoca.cartApi.entities.concretes.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product,String> {
}
