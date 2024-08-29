package com.example.shopapp.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class RestaurantDTO {
    private Long id;
    private String title;
    @Column(length = 1000)
    private String images;
    private String description;
}
