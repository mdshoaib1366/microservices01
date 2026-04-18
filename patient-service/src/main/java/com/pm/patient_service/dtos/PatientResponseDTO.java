package com.pm.patient_service.dtos;


import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientResponseDTO {
    private UUID id;
    private String email;
    private String name;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private LocalDate registeredDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
