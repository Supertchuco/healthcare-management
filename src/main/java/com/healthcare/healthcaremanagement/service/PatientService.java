package com.healthcare.healthcaremanagement.service;

import com.healthcare.healthcaremanagement.dto.ExamDto;
import com.healthcare.healthcaremanagement.entity.Patient;
import com.healthcare.healthcaremanagement.enumerator.Gender;
import com.healthcare.healthcaremanagement.exception.InvalidCPFException;
import com.healthcare.healthcaremanagement.exception.InvalidGenderException;
import com.healthcare.healthcaremanagement.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.apache.commons.lang3.StringUtils.length;

@Service
@Slf4j
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    public Patient initializePatient(final ExamDto examDto) {
        log.info("Initialize patient object");
        validateCPF(examDto.getPatientCpf());
        validateGender(examDto.getPatientGender());
        Patient patient = patientRepository.findByCpf(examDto.getPatientCpf());
        if (isNull(patient)) {
            log.info("Patient not exist on database");
            patient = new Patient(examDto.getPatientCpf(), examDto.getPatientName(), examDto.getPatientAge(),
                    examDto.getPatientGender());
        }
        return patient;
    }

    private void validateCPF(String cpf) {
        cpf = cpf.replaceAll("[./-]", "");
        if (length(cpf) != 11 || !isNumeric(cpf)) {
            log.error("Invalid CNPJ {}", cpf);
            throw new InvalidCPFException();
        }
    }

    private void validateGender(final String gender) {
        if (!EnumUtils.isValidEnum(Gender.class, gender)) {
            log.error("Invalid gender value {}", gender);
            throw new InvalidGenderException();
        }
    }
}
