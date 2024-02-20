package com.enoca.cartApi.entities.concretes;

import com.enoca.cartApi.entities.abstracts.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {

    @Column(name="total_price")
    private Double totalPrice;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name="date")
    private LocalDateTime date;

    @Column(name="code",unique=true)
    private String code;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name="quantity")
    private Long quantity;

    @ManyToOne
    @JoinColumn(name="customer_id")
    @JsonIgnore
    private Customer customer;

}
