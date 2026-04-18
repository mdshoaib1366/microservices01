package com.pm.patient_service.dtos;

import com.pm.patient_service.dtos.validators.CreatePatientValidationGroup;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PatientRequestDTO {

    @NotBlank(groups = CreatePatientValidationGroup.class, message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(groups = CreatePatientValidationGroup.class, message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(groups = CreatePatientValidationGroup.class, message = "Phone number is required")
    @Pattern(regexp = "^[0-9]+$", message = "Phone number must contain only digits")
    @Size(max = 12, message = "Phone number cannot exceed 12 digits")
    private String phone;

    @NotBlank(groups = CreatePatientValidationGroup.class, message = "Address is required")
    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String address;

    @NotNull(groups = CreatePatientValidationGroup.class, message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull(groups = CreatePatientValidationGroup.class
            , message = "Registered date is required")
    @PastOrPresent(message = "Registered date cannot be in the future")
    private LocalDate registeredDate;

}
