package com.example.shopapp.request;

import com.example.shopapp.entity.Address;
import com.example.shopapp.entity.Category;
import com.example.shopapp.entity.ContactInformation;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantRequest {
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private Category categories;
    private String openingHours;
    private String images;
}
