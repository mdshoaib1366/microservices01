package com.fitness.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "username is required")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,}$",
            message = "Username must be at least 5 characters and include both letters and a digit")
    private String username;

    @NotBlank(message = "password is required")
    @Size(min = 5, message = "Password must have at least 12 characters")
    private String password;

    @Pattern(regexp = "\\d{6,12}", message = "Phone must contain 6 to 12 digits")
    private String phone;

//    private UserRole role;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
}
