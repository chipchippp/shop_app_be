package com.example.shopapp.respository;


import com.example.shopapp.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    public Cart findCartByUserId(Long userId);
}
