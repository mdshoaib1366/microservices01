package com.pm.patient_service.controllers;

import com.pm.patient_service.dtos.PatientRequestDTO;
import com.pm.patient_service.dtos.PatientResponseDTO;
import com.pm.patient_service.dtos.validators.CreatePatientValidationGroup;
import com.pm.patient_service.dtos.validators.UpdatePatientValidationGroup;
import com.pm.patient_service.services.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("patients")
@Tag(name = "Patient", description = "API for managing Patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new Patient")
    public ResponseEntity<PatientResponseDTO> registerPatient(
            @Validated(CreatePatientValidationGroup.class)
            @RequestBody PatientRequestDTO dto){
        PatientResponseDTO patientResponseDTO = patientService.registerPatient(dto);
        return new ResponseEntity<>(patientResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping()
    @Operation(summary = "Get All Patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatients(){
        List<PatientResponseDTO> patients = patientService.getPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update a Patient")
    public ResponseEntity<PatientResponseDTO> updatePatient(
            @PathVariable UUID id,
            @Validated({UpdatePatientValidationGroup.class, Default.class})
            @RequestBody PatientRequestDTO requestDTO){
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(id, requestDTO);
        return new ResponseEntity<>(patientResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{id}")
    @Operation(summary = "Delete a Patient")
    public ResponseEntity<String> deletePatient(@PathVariable UUID id){
        patientService.deletePatient(id);
        return new ResponseEntity<>("Successfully Deleted !!", HttpStatus.OK);
    }


}
