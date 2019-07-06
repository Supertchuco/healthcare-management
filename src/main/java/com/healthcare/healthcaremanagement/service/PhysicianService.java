package com.healthcare.healthcaremanagement.service;

import com.healthcare.healthcaremanagement.dto.ExamDto;
import com.healthcare.healthcaremanagement.entity.Physician;
import com.healthcare.healthcaremanagement.repository.PhysicianRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class PhysicianService {

    @Autowired
    private PhysicianRepository physicianRepository;

    public Physician initializePhysician(final ExamDto examDto) {
        log.info("Initialize physician object");
        Physician physician = physicianRepository.findByCrm(examDto.getPhysicianCrm());
        if (isNull(physician)) {
            log.info("Patient not exist on database");
            physician = new Physician(examDto.getPhysicianCrm(), examDto.getPhysicianName());
        }
        return physician;
    }
}
