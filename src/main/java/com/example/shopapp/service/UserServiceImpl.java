package com.example.shopapp.service;

import com.example.shopapp.config.JwtProvider;
import com.example.shopapp.dto.UserDTO;
import com.example.shopapp.dto.UserLoginDTO;
import com.example.shopapp.entity.Cart;
import com.example.shopapp.entity.UserEntity;
import com.example.shopapp.enums.ERole;
import com.example.shopapp.response.AuthResponse;
import com.example.shopapp.respository.CartRepository;
import com.example.shopapp.respository.UserRepository;
import com.example.shopapp.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailService userDetailService;
    private final CartRepository cartRepository;

    @Override
    public UserEntity findByUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        UserEntity userEntity = userRepository.findUserByEmail(email);
        if (userEntity == null) {
            throw new Exception("User not found with email: " + email);
        }
        return userEntity;
    }

    @Override
    public UserEntity findUserByEmail(String email) throws Exception {
        UserEntity userEntity = userRepository.findUserByEmail(email);
        if (userEntity == null) {
            throw new Exception("User not found with email: " + email);
        }
        return userEntity;
    }

    @Override
    public AuthResponse login(UserLoginDTO userLoginDTO) throws Exception {
        String email = userLoginDTO.getEmail();
        String password = userLoginDTO.getPassword();

        // Xác thực người dùng
        Authentication authentication = authenticate(email, password);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        // Tạo JWT token
        String jwt = jwtProvider.generateToken(authentication);

        // Tạo phản hồi đăng nhập
        AuthResponse authResponse = new AuthResponse();
        authResponse.setId(userRepository.findUserByEmail(email).getId());  // Thêm ID của người dùng
        authResponse.setUsername(userRepository.findUserByEmail(email).getUsername()); // Thêm username của người dùng
        authResponse.setEmail(email);
        authResponse.setToken(jwt);
        authResponse.setRole(ERole.valueOf(role));
        authResponse.setMessage("Login successfully");

        return authResponse;
    }

    private Authentication authenticate(String email, String password) {
        // Tải thông tin người dùng từ UserDetailService
        UserDetails userDetails = userDetailService.loadUserByUsername(email);
        if (userDetails == null) {
            throw new BadCredentialsException("User not found");
        }

        // Kiểm tra mật khẩu
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Password is incorrect");
        }

        // Tạo đối tượng Authentication
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public AuthResponse register(UserDTO userDTO) throws Exception {
        // Kiểm tra email
        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            throw new Exception("Email cannot be null or empty");
        }

        // Kiểm tra xem email đã tồn tại chưa
        UserEntity isEmailExists = userRepository.findUserByEmail(userDTO.getEmail());
        if (isEmailExists != null) {
            throw new Exception("Email already exists");
        }

        // Tạo người dùng mới
        UserEntity newUser = new UserEntity();
        newUser.setUsername(userDTO.getUsername());
        newUser.setEmail(userDTO.getEmail());
        newUser.setRole(userDTO.getRole());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Lưu người dùng vào cơ sở dữ liệu
        UserEntity savedUser = userRepository.save(newUser);

        // Tạo giỏ hàng cho người dùng
        Cart cart = new Cart();
        cart.setUser(savedUser);
        cartRepository.save(cart);

        // Xác thực người dùng sau khi đăng ký
        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Tạo JWT token
        String jwt = jwtProvider.generateToken(authentication);

        // Tạo phản hồi đăng ký
        AuthResponse authResponse = new AuthResponse();
        authResponse.setId(savedUser.getId());
        authResponse.setUsername(savedUser.getUsername());
        authResponse.setEmail(savedUser.getEmail());
        authResponse.setToken(jwt);
        authResponse.setMessage("Register successfully");
        authResponse.setRole(savedUser.getRole());

        return authResponse;
    }
}
