package com.example.shopapp.controller;

import com.example.shopapp.dto.RestaurantDTO;
import com.example.shopapp.dto.RestaurantImageDTO;
import com.example.shopapp.entity.Restaurant;
import com.example.shopapp.entity.RestaurantImage;
import com.example.shopapp.entity.UserEntity;
import com.example.shopapp.response.ApiResponse;
import com.example.shopapp.response.MessageResponse;
import com.example.shopapp.service.impl.RestaurantService;
import com.example.shopapp.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> saveRestaurant(
            @RequestBody RestaurantDTO res,
            @RequestHeader("Authorization") String jwt

    ) throws Exception {
        UserEntity user = userService.findByUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.saveRestaurant(res, user);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PostMapping(value ="/uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(
            @RequestHeader("Authorization") String jwt,
            @ModelAttribute("files") List<MultipartFile> files,
            @PathVariable Long id
    ) throws Exception {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            Restaurant existingRestaurant = restaurantService.findRestaurantById(id);
            List<RestaurantImage> restaurantImages = new ArrayList<>();
            if (files != null) {
                return ResponseEntity.badRequest().body("Please select a file!");
            }
            files = files == null ? new ArrayList<>() : files;
            for (MultipartFile file : files){
                if (file.getSize() == 0){
                    continue;
                }
                if (file.getSize()> 10*1024*1024){
                    return ResponseEntity.badRequest().body("File size too large!");
                }
                if (!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png")){
                    return ResponseEntity.badRequest().body("File type not supported!");
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")){
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("Unsupported media type, only accept image");
                }
                String fileName = storeFile(file);
                RestaurantImage restaurantImage = restaurantService.createRestaurantImage(
                        existingRestaurant.getId(),
                        RestaurantImageDTO.builder()
                                .imgUrl(fileName)
                                .build());
                        restaurantImages.add(restaurantImage);
            }
            return ResponseEntity.ok().body(restaurantImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        if (!isImage(file) || file.getOriginalFilename() != null) {
            throw new IOException("Unsupported media type, only accept image");
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    private boolean isImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @RequestBody RestaurantDTO res,
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        UserEntity user = userService.findByUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.updateRestaurant(id, res);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<RestaurantDTO> addToFavorites(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        UserEntity user = userService.findByUserByJwtToken(jwt);
        RestaurantDTO restaurantDTO = restaurantService.addToFavorites(id, user);
        return new ResponseEntity<>(restaurantDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        UserEntity user = userService.findByUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.updateRestaurantStatus(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping()
    public ApiResponse<?> getAllRestaurant(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "limit") int limit
    ) throws Exception {
        return ApiResponse.builder()
                .message("Get All Restaurants with per page")
                .data(restaurantService.getAllRestaurants(PageRequest.of(page - 1, limit)))
                .build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(
            @RequestHeader("Authorization") String jwt,
            @RequestParam("query") String keyWord
    ) throws Exception {

        UserEntity user = userService.findByUserByJwtToken(jwt);
        List<Restaurant> restaurant = restaurantService.searchRestaurant(keyWord);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {

        UserEntity user = userService.findByUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Restaurant> getRestaurantByUserId(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserEntity user = userService.findByUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        UserEntity user = userService.findByUserByJwtToken(jwt);
        restaurantService.deleteRestaurant(id);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Restaurant deleted successfully");
        return new ResponseEntity<>(messageResponse, HttpStatus.NO_CONTENT);
    }



}
