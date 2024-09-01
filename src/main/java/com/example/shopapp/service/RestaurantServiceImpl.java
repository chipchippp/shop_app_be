package com.example.shopapp.service;

import com.example.shopapp.dto.RestaurantDTO;
import com.example.shopapp.dto.RestaurantImageDTO;
import com.example.shopapp.entity.*;
import com.example.shopapp.exception.InvalidParamException;
import com.example.shopapp.mapper.RestaurantMapper;
import com.example.shopapp.respository.*;
import com.example.shopapp.service.impl.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final RestaurantImageRepository restaurantImageRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Restaurant saveRestaurant(RestaurantDTO restaurantDTO, UserEntity userEntity) throws Exception {
        Restaurant restaurant = Restaurant.builder()
                .name(restaurantDTO.getName())
                .description(restaurantDTO.getDescription())
                .cuisineType(restaurantDTO.getCuisineType())
                .owner(userEntity)
                .open(true)
                .registeredAt(LocalDateTime.now())
                .build();
        Address address = Address.builder()
                .city(restaurantDTO.getAddress().getCity())
                .country(restaurantDTO.getAddress().getCountry())
                .street(restaurantDTO.getAddress().getStreet())
                .zipCode(restaurantDTO.getAddress().getZipCode())
                .build();
        addressRepository.save(address);
        restaurant.setAddress(address);

        Category category = categoryRepository.findById(restaurantDTO.getCategoryId()).orElseThrow(() -> new Exception("Category not found"));
        restaurant.setCategory(category);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long id, RestaurantDTO restaurantDTO) throws Exception {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new Exception("updateRestaurant not found"));
        if (restaurant.getCuisineType() != null) restaurant.setCuisineType(restaurantDTO.getCuisineType());
        if (restaurant.getDescription() != null) restaurant.setDescription(restaurantDTO.getDescription());
        if (restaurant.getName() != null) restaurant.setName(restaurantDTO.getName());

        restaurantRepository.save(restaurant);
        return restaurant;
    }

    @Override
    public void deleteRestaurant(Long id) throws Exception {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new Exception("deleteRestaurant not found"));
        restaurantRepository.delete(restaurant);
    }

    @Override
    public Page<RestaurantDTO> getAllRestaurants(PageRequest pageRequest) throws Exception {
        Page<Restaurant> restaurants = restaurantRepository.findAll(pageRequest);
        return restaurants.map(RestaurantMapper.INSTANCE::toRestaurantDTO);
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyWord) throws Exception {
        return restaurantRepository.findBySearchQuery(keyWord);
    }

    @Override
    public Restaurant getRestaurantByUserId(Long id) throws Exception {
        return restaurantRepository.findByOwnerId(id);
    }

    @Override
    public Restaurant findRestaurantById(Long userId) throws Exception {
        return restaurantRepository.findById(userId).orElseThrow(() -> new Exception("Restaurant not found"));
    }

    @Override
    public RestaurantDTO addToFavorites(Long restaurantId, UserEntity userEntity) throws Exception {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new Exception("addToFavorites not found"));
        RestaurantDTO restaurantDto = new RestaurantDTO();
        restaurantDto.setId(restaurant.getId());
        restaurantDto.setName(restaurant.getName());
        restaurantDto.setCuisineType(restaurant.getCuisineType());
        restaurantDto.setOpeningHours(restaurant.getOpeningHours());
        restaurantDto.setImages(restaurant.getImages());
        restaurantDto.setDescription(restaurant.getDescription());

        boolean isFavorite = false;
        List<RestaurantDTO> favorites = userEntity.getFavorites();
        for (RestaurantDTO favorite : favorites) {
            if (favorite.getId().equals(restaurantId)) {
                isFavorite = true;
                break;
            }
        }

        if (isFavorite) {
            favorites.remove(restaurantDto);
        } else {
            favorites.add(restaurantDto);
        }
        userRepository.save(userEntity);

        return restaurantDto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new Exception("updateRestaurantStatus not found"));
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }

    @Override
    public RestaurantImage createRestaurantImage(Long restaurantId, RestaurantImageDTO restaurantImageDTO) throws Exception {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new Exception("createRestaurantImage not found"));
        RestaurantImage restaurantImage = RestaurantImage.builder()
                .restaurant(restaurant)
                .imgUrl(restaurantImageDTO.getImgUrl())
                .build();
        int size = restaurantImageRepository.findByRestaurantId(restaurantId).size();
        if (size >= RestaurantImage.MAXIMUM_IMAGE_PER_RESTAURANT) {
            throw new InvalidParamException("Product image size must be =< "
                    + RestaurantImage.MAXIMUM_IMAGE_PER_RESTAURANT);
        }
        return restaurantImageRepository.save(restaurantImage);
    }
}
