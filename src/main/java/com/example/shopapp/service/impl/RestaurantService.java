package com.example.shopapp.service.impl;

import com.example.shopapp.dto.UserDTO;
import com.example.shopapp.entity.Restaurant;
import com.example.shopapp.entity.UserEntity;
import com.example.shopapp.request.RestaurantRequest;
import com.example.shopapp.response.RestaurantResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RestaurantService {
    Restaurant saveRestaurant(RestaurantRequest res, UserEntity userEntity) throws Exception;
    Restaurant updateRestaurant(Long id, RestaurantRequest updateRes) throws Exception;
    void deleteRestaurant(Long id) throws Exception;
    Page<RestaurantResponse> getAllRestaurants(PageRequest pageRequest) throws Exception;
    Page<RestaurantResponse> searchRestaurant(String keyWord, PageRequest pageRequest) throws Exception;
    Restaurant getRestaurantByUserId(Long id) throws Exception;
    Restaurant findRestaurantById(Long userId) throws Exception;
    Restaurant addToFavorites(Long restaurantId, UserDTO userDTO) throws Exception;
    Restaurant updateRestaurantStatus(Long id) throws Exception;
}
