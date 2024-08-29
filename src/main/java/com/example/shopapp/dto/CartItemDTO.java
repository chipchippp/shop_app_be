package com.example.shopapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long id;
    private Long foodId;  // Chỉ lưu ID của món ăn
    private Long quantity;
    private String ingredients;
    private Long totalPrice;
}
