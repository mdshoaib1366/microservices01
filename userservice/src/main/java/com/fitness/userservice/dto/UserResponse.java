package com.fitness.userservice.dto;

import com.fitness.userservice.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private UUID userId;
    private String username;
    private String email;
    private Long phone;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
