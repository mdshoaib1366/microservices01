package com.pm.patient_service.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(unique = true)
    private String email;

    private String name;

    private String phone;

    private String address;

    private LocalDate dateOfBirth;

    private LocalDate registeredDate;

    @Column(nullable = false, updatable = false)
    @CurrentTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime updateAt;
}


