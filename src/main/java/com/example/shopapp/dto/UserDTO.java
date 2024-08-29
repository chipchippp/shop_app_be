package com.example.shopapp.dto;

import com.example.shopapp.enums.ERole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;

    @JsonProperty("confirm_password")
    private String confirmPassword;

    private ERole role = ERole.ROLE_USER;

    private List<CartDTO> carts = new ArrayList<>();
    private List<RestaurantDTO> favorites = new ArrayList<>();
    private List<AddressDTO> addresses = new ArrayList<>();
}
