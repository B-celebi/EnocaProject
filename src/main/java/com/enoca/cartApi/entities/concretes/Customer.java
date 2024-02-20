package com.enoca.cartApi.entities.concretes;

import com.enoca.cartApi.entities.abstracts.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseEntity {

    @NotBlank
    @Size(min=3,max=40)
    @Column(name="name")
    private String name;

    @NotBlank
    @Size(min=3,max=40)
    @Column(name="surname")
    private String surname;

    @OneToOne(mappedBy = "customer")
    private Cart cart;

    @OneToMany(mappedBy="customer")
    private List<Order> orders;

}
