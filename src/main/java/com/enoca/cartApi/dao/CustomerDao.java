package com.enoca.cartApi.dao;

import com.enoca.cartApi.entities.concretes.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDao extends JpaRepository<Customer,String> {
}
