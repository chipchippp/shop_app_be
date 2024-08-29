package com.example.shopapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long id;
    private Long userId;  // Chỉ lưu ID của người dùng, tránh vòng lặp
    private Double total;

    // Danh sách các item trong giỏ hàng
    private List<CartItemDTO> cartItems = new ArrayList<>();
}
