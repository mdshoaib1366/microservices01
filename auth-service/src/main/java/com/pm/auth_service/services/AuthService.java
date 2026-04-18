package com.pm.auth_service.services;

import com.pm.auth_service.dtos.LoginRequestDTO;
import com.pm.auth_service.dtos.LoginResponseDTO;
import com.pm.auth_service.entities.User;
import com.pm.auth_service.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Optional<String> authenticate(LoginRequestDTO requestDTO){
        Optional<String> token = userService.findByEmail(requestDTO.getEmail())
                .filter(u -> passwordEncoder.matches(requestDTO.getPassword(),
                        u.getPassword()))
                .map(u-> jwtUtil.generateToken(u.getEmail(), u.getRole()));

        return token;
    }

    public boolean validateToken(String token) {
        try {
            jwtUtil.validateToken(token);
            return true;
        } catch (JwtException e) {
            System.out.println("Error in validating token..");
            return false;
        }

    }
}
