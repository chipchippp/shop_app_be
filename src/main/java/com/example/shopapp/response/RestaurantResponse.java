package com.example.shopapp.response;

import com.example.shopapp.entity.Address;
import com.example.shopapp.entity.ContactInformation;
import com.example.shopapp.entity.Restaurant;
import com.example.shopapp.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantResponse {
    private Long id;
    private String name;

    private Address address;
    private UserEntity owner;

    private String description;

    @JsonProperty("cuisine_type")
    private String cuisineType;

    @JsonProperty("opening_hours")
    private String openingHours;

    @JsonProperty("images")
    private List<String> images;

    @JsonProperty("order_id")
    private Long order;

    @JsonProperty("food_id")
    private Long food;

    @JsonProperty("contact_information")
    private ContactInformation contactInformation;

    private LocalDateTime registeredAt;
    private boolean open;

    public static RestaurantResponse fromRestaurant(Restaurant restaurant) {
        RestaurantResponse restaurantResponse = RestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .description(restaurant.getDescription())
                .cuisineType(restaurant.getCuisineType())
                .openingHours(restaurant.getOpeningHours())
                .images(restaurant.getImages())
                .address(restaurant.getAddress())
                .owner(restaurant.getOwner())
                .contactInformation(restaurant.getContactInformation())
                .registeredAt(restaurant.getRegisteredAt())
                .open(restaurant.isOpen())
                .build();
        return restaurantResponse;
    }
}
