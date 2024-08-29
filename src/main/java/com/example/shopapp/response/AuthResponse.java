package com.example.shopapp.response;

import com.example.shopapp.enums.ERole;
import lombok.Data;

@Data
public class AuthResponse {
    private Long id;
    private String username;
    private String email;
    private String token;
    private String message;
    private ERole role;
}
