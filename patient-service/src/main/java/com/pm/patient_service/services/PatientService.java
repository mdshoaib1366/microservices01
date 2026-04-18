package com.pm.patient_service.services;

import billing.BillingResponse;
import com.pm.patient_service.dtos.PatientRequestDTO;
import com.pm.patient_service.dtos.PatientResponseDTO;
import com.pm.patient_service.entities.Patient;
import com.pm.patient_service.exceptions.EmailExistsException;
import com.pm.patient_service.grpc.BillingServiceGrpcClient;
import com.pm.patient_service.kafka.KafkaProducer;
import com.pm.patient_service.repositories.PatientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public PatientResponseDTO registerPatient(PatientRequestDTO dto) {
        if (patientRepository.existsByEmail(dto.getEmail())) {
            throw new EmailExistsException("A patient with this email" +
                    " already exists: " + dto.getEmail());
        }

        Patient patient = this.requestToEntity(dto);
        Patient savedPatient = patientRepository.save(patient);

        BillingResponse response = billingServiceGrpcClient.creatingBillingAccount(savedPatient.getId().toString(),
                savedPatient.getEmail(), savedPatient.getName(), savedPatient.getPhone());

        kafkaProducer.sendEvent(savedPatient);

        return this.entityToResponse(savedPatient);
    }

    public List<PatientResponseDTO> getPatients() {
        return patientRepository.findAll().stream().map(this::entityToResponse).toList();
    }


    @Transactional
    public PatientResponseDTO updatePatient(UUID id, @Valid PatientRequestDTO requestDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));

        if (patientRepository.existsByEmailAndIdNot(requestDTO.getEmail(), id)) {
            throw new RuntimeException("A patient with this email" +
                    " already exists: " + requestDTO.getEmail());
        }

        if (requestDTO.getName() != null)
            patient.setName(requestDTO.getName());
        if (requestDTO.getEmail() != null)
            patient.setEmail(requestDTO.getEmail());
        if (requestDTO.getAddress() != null)
            patient.setAddress(requestDTO.getAddress());
        if (requestDTO.getPhone() != null)
            patient.setPhone(requestDTO.getPhone());
        if (requestDTO.getDateOfBirth() != null)
            patient.setDateOfBirth(requestDTO.getDateOfBirth());
        patient.setUpdateAt(LocalDateTime.now());

        Patient savedPatient = patientRepository.save(patient);
        return this.entityToResponse(savedPatient);
    }

    @Transactional
    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }

    private Patient requestToEntity(PatientRequestDTO dto) {
        return Patient.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .dateOfBirth(dto.getDateOfBirth())
                .registeredDate(dto.getRegisteredDate())
                .build();
    }

    private PatientResponseDTO entityToResponse(Patient patient) {
        return PatientResponseDTO.builder()
                .id(patient.getId())
                .email(patient.getEmail())
                .name(patient.getName())
                .phone(patient.getPhone())
                .address(patient.getAddress())
                .dateOfBirth(patient.getDateOfBirth())
                .registeredDate(patient.getRegisteredDate())
                .createdAt(patient.getCreatedAt())
                .updatedAt(patient.getUpdateAt())
                .build();
    }

}
