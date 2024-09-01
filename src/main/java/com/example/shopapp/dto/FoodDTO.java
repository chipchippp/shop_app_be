package com.example.shopapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodDTO {
    private String name;
    private String description;
    private Double price;
    private String category;
    private String images;
    private CategoryDTO categoryDTO;
    private RestaurantDTO restaurantDTO;
    private Long quantity;
    private boolean available;
    private boolean isSeasonal;
    private boolean isVegan;
    private Date createdAt;
}
