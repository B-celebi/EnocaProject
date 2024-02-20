package com.enoca.cartApi.entities.concretes;

import com.enoca.cartApi.entities.abstracts.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="carts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart extends BaseEntity{

    @Min(0)
    @Column(name="total_price")
    private Double totalPrice;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", unique = true)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Customer customer;

    @OneToMany(mappedBy="productCart")
    private Set<CartProduct> cartProducts;
}
