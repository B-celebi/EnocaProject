package com.enoca.cartApi.dao;

import com.enoca.cartApi.entities.concretes.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface OrderDao extends JpaRepository<Order,String> {
    List<Order> findAllByCustomerId(@PathVariable String id);
    Order findOrderByCode(String id);
}
