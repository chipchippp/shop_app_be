package com.example.shopapp.service;

import com.example.shopapp.dto.UserDTO;
import com.example.shopapp.entity.Address;
import com.example.shopapp.entity.Restaurant;
import com.example.shopapp.entity.UserEntity;
import com.example.shopapp.request.RestaurantRequest;
import com.example.shopapp.respository.AddressRepository;
import com.example.shopapp.respository.RestaurantRepository;
import com.example.shopapp.respository.UserRepository;
import com.example.shopapp.service.impl.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.example.shopapp.response.RestaurantResponse;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public Restaurant saveRestaurant(RestaurantRequest res, UserEntity userEntity) throws Exception {
        Address address = addressRepository.save(res.getAddress());
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(res.getContactInformation());
        restaurant.setCuisineType(res.getCuisineType());
        restaurant.setDescription(res.getDescription());
        restaurant.setImages(res.getImages());
        restaurant.setName(res.getName());
        restaurant.setOpeningHours(res.getOpeningHours());
        restaurant.setRegisteredAt(LocalDateTime.now());
        restaurant.setOwner(userEntity);
        if (restaurant == null) {
            throw new Exception("Restaurant entity must not be null");
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long id, RestaurantRequest updateRes) throws Exception {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new Exception("updateRestaurant not found"));
        if (restaurant.getCuisineType() != null) restaurant.setCuisineType(updateRes.getCuisineType());
        if (restaurant.getDescription() != null) restaurant.setDescription(updateRes.getDescription());
        if (restaurant.getName() != null) restaurant.setName(updateRes.getName());

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long id) throws Exception {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new Exception("deleteRestaurant not found"));
        restaurantRepository.delete(restaurant);
    }

    @Override
    public Page<RestaurantResponse> getAllRestaurants(PageRequest pageRequest) throws Exception {
        return restaurantRepository
                .findAll(pageRequest)
                .map(RestaurantResponse::fromRestaurant);
    }

    @Override
    public Page<RestaurantResponse> searchRestaurant(String keyWord, PageRequest pageRequest) throws Exception {
        return null;
    }

    @Override
    public Restaurant getRestaurantByUserId(Long id) throws Exception {
        return null;
    }

    @Override
    public Restaurant findRestaurantById(Long userId) throws Exception {
        return null;
    }

    @Override
    public Restaurant addToFavorites(Long restaurantId, UserDTO userDTO) throws Exception {
        return null;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        return null;
    }
}
