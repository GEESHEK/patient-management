package com.gee.patientservice.service;

import com.gee.patientservice.dto.PatientRequestDto;
import com.gee.patientservice.exception.EmailAlreadyExistsException;
import com.gee.patientservice.mapper.PatientMapper;
import com.gee.patientservice.model.Patient;
import com.gee.patientservice.dto.PatientResponseDTO;
import com.gee.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDto patientRequestDto) {
        if(patientRepository.existsByEmail(patientRequestDto.getEmail())) {
            throw new EmailAlreadyExistsException("A patient with this email already exist: " + patientRequestDto.getEmail());
        }

        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDto));
        return PatientMapper.toDTO(newPatient);
    }
}
