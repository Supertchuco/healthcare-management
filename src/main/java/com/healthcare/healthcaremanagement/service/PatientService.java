package com.healthcare.healthcaremanagement.service;

import com.healthcare.healthcaremanagement.dto.ExamDto;
import com.healthcare.healthcaremanagement.entity.Patient;
import com.healthcare.healthcaremanagement.exception.InvalidCpfException;
import com.healthcare.healthcaremanagement.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    public Patient initializePatient(final ExamDto examDto) {
        log.info("Initialize patient object");
        validateCpf(examDto.getPatientCpf());
        Patient patient = patientRepository.findByCpf(examDto.getPatientCpf());
        if (isNull(patient)) {
            log.info("Patient not exist on database");
            patient = new Patient(examDto.getPatientCpf(), examDto.getPatientName(), examDto.getPatientAge(),
                    examDto.getPatientGender());
        }
        return patient;
    }

    private void validateCpf(final String cpf){
        if(StringUtils.length(cpf) != 9 || !StringUtils.isAlphanumeric(cpf)){
            log.error("Invalid CPF {}", cpf);
            throw new InvalidCpfException();
        }
    }
}
