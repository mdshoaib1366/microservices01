package com.fitness.userservice.service;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;

import java.util.UUID;

public interface UserService {
    public UserResponse getUserProfile(UUID id);
    public UserResponse register(RegisterRequest registerRequest);
    public Boolean existByUserId(UUID userId);
}
