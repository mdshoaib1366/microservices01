package com.pm.auth_service.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
//    private String email;
//    private String Role;
}
