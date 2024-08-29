package com.example.shopapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Address address;
    private String status;
    @ManyToOne
    private UserEntity user;
    @JsonIgnore
    @ManyToOne
    private Restaurant restaurant;
    @OneToMany
    private List<OrderItem> orderItems;
    private Double totalPrice;
    private Long totalItems;
    private Date createdAt;
}
