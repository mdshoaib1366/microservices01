package com.nit.mybank.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {
    private String name;
    private String email;
    private String password;
}
