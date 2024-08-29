package com.example.shopapp.controller;

import com.example.shopapp.dto.UserDTO;
import com.example.shopapp.dto.UserLoginDTO;
import com.example.shopapp.entity.UserEntity;
import com.example.shopapp.response.AuthResponse;
import com.example.shopapp.service.impl.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errors = result.getFieldErrors()
                        .stream()
                        .map(error -> error.getDefaultMessage())
                        .toList();
                return ResponseEntity.badRequest().body(errors);
            }
            if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
                return ResponseEntity.badRequest().body("Password not match");
            }
            AuthResponse authResponse = userService.register(userDTO);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login( @Valid @RequestBody UserLoginDTO userLoginDTO,
                                    BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errors = result.getFieldErrors()
                        .stream()
                        .map(error -> error.getDefaultMessage())
                        .toList();
                return ResponseEntity.badRequest().body(errors);
            }
            AuthResponse authResponse = userService.login(userLoginDTO);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<UserEntity> findUserByJwtToken(
            @RequestHeader("Authorization") String jwt
    ) {
        try {
            UserEntity user = userService.findByUserByJwtToken(jwt);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/user")
    public ResponseEntity<UserEntity> getUserByEmail(String email) {
        try {
            UserEntity user = userService.findUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
