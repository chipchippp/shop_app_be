package com.example.shopapp.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@Builder
@Data
@AllArgsConstructor
@Entity
@Table(name = "restaurant_images")
public class RestaurantImage {
    public static final int MAXIMUM_IMAGE_PER_RESTAURANT = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "img_url", length = 300)
    private String imgUrl;
}