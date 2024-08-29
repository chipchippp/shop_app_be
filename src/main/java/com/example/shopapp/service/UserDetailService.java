package com.example.shopapp.service;

import com.example.shopapp.entity.UserEntity;
import com.example.shopapp.enums.ERole;
import com.example.shopapp.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException("loadUserByUsername not found with email: " + email);
        }
        ERole role = userEntity.getRole();

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        return new User(userEntity.getEmail(), userEntity.getPassword(), authorities);
    }
}
