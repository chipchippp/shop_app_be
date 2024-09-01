package com.example.shopapp.dto;

import com.example.shopapp.entity.Address;
import com.example.shopapp.entity.ContactInformation;
import com.example.shopapp.entity.Food;
import lombok.Data;

@Data
public class RestaurantDTO {
    private Long id;
    private String name;
    private String cuisineType;
    private String openingHours;
    private String images;
    private Long categoryId;
    private Long ownerId;
    private Food food;
    private ContactInformation contactInformation;
    private Address address;
    private String description;
}