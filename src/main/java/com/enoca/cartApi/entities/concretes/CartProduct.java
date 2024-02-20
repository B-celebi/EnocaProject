package com.enoca.cartApi.entities.concretes;

import com.enoca.cartApi.entities.abstracts.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name="cart_products")
@AllArgsConstructor
@NoArgsConstructor
public class CartProduct extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Min(1)
    private Long quantity;

    @ManyToOne
    @JoinColumn(name="product_cart_id")
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Cart productCart;
}
