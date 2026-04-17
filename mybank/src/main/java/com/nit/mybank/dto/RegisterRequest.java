package com.nit.mybank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
}
