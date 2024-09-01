package com.example.shopapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "foods")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;

    @ElementCollection
    @Column(length = 1000)
    private List<String> images;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Restaurant restaurant;
    private Long quantity;
    private boolean available;
    private boolean isSeasonal;
    private boolean isVegan;
    private Date createdAt;

}
