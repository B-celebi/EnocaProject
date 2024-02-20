package com.enoca.cartApi.entities.concretes;

import com.enoca.cartApi.entities.abstracts.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {

    @Column
    @Min(0)
    private Double price;
    @NotBlank
    @Column
    private String name;
    @Min(value = 0,message="must be greater than or equal to 1")
    @Column
    private Long stock;

}
