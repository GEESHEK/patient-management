package com.gee.patientservice.contoller;

import com.gee.patientservice.dto.PatientRequestDto;
import com.gee.patientservice.dto.PatientResponseDTO;
import com.gee.patientservice.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/patients") //http://localhost:4000/patients
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        List<PatientResponseDTO> patientResponseDTOS = patientService.getPatients();
        return ResponseEntity.ok().body(patientResponseDTOS);
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDto patientRequestDto) {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(patientResponseDTO.getId())
                .toUri();

        return ResponseEntity.created(location).body(patientResponseDTO);
    }

}
