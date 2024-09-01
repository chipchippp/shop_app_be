package com.example.shopapp.mapper;

import com.example.shopapp.dto.*;
import com.example.shopapp.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    RestaurantDTO toRestaurantDTO(Restaurant restaurant);
    Restaurant restaurantDTOtoRestaurant(RestaurantDTO restaurantDTO);
    List<RestaurantDTO> toRestaurantDTOList(List<Restaurant> restaurants);
    List<Restaurant> restaurantDTOListToRestaurantList(List<RestaurantDTO> restaurantDTOs);

}