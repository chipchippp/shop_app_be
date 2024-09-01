package com.example.shopapp.service.impl;

import com.example.shopapp.dto.RestaurantDTO;
import com.example.shopapp.dto.RestaurantImageDTO;
import com.example.shopapp.entity.Restaurant;
import com.example.shopapp.entity.RestaurantImage;
import com.example.shopapp.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface RestaurantService {
    Restaurant saveRestaurant(RestaurantDTO restaurantDTO, UserEntity userEntity) throws Exception;
    Restaurant updateRestaurant(Long id, RestaurantDTO restaurantDTO) throws Exception;
    void deleteRestaurant(Long id) throws Exception;
    Page<RestaurantDTO> getAllRestaurants(PageRequest pageRequest) throws Exception;
    List<Restaurant> searchRestaurant(String keyWord) throws Exception;
    Restaurant getRestaurantByUserId(Long id) throws Exception;
    Restaurant findRestaurantById(Long userId) throws Exception;
    RestaurantDTO addToFavorites(Long restaurantId, UserEntity userEntity) throws Exception;
    Restaurant updateRestaurantStatus(Long id) throws Exception;

    RestaurantImage createRestaurantImage(
            Long restaurantId,
            RestaurantImageDTO restaurantImageDTO
    ) throws Exception;
}
