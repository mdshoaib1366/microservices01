package com.fitness.userservice.service.Impl;

import com.fitness.userservice.constant.UserRole;
import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.entity.User;
import com.fitness.userservice.repository.UsersRepository;
import com.fitness.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final static Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UsersRepository userRepository;

    @Override
    public UserResponse getUserProfile(UUID id) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new RuntimeException("User not found! with id: "+id));

        return UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    @Override
    @Transactional
    public UserResponse register(RegisterRequest registerRequest) {

        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new RuntimeException("Email is already exist!");
        }

        User newUser = User.builder()
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .email(registerRequest.getEmail())
                .phone(Long.parseLong(registerRequest.getPhone()))
                .role(UserRole.USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(newUser);

        return UserResponse.builder()
                .userId(savedUser.getUserId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .phone(savedUser.getPhone())
                .role(savedUser.getRole())
                .createdAt(savedUser.getCreatedAt())
                .updatedAt(savedUser.getUpdatedAt())
                .build();
    }

    @Override
    public Boolean existByUserId(UUID userId) {
        LOG.info("Calling User Validation API for userId: {}",userId);
        return userRepository.existsById(userId);
    }
}
