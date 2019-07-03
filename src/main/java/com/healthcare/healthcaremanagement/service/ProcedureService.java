package com.healthcare.healthcaremanagement.service;

import com.healthcare.healthcaremanagement.dto.ExamDto;
import com.healthcare.healthcaremanagement.entity.Procedure;
import com.healthcare.healthcaremanagement.repository.ProcedureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class ProcedureService {

    @Autowired
    private ProcedureRepository procedureRepository;

    public Procedure initializeProcedure(final ExamDto examDto) {
        log.info("Initialize procedure object");
        Procedure procedure = procedureRepository.findByName(examDto.getProcedure());
        if (isNull(procedure)) {
            log.info("Patient not exist on database");
            procedure = new Procedure(examDto.getProcedure());
        }
        return procedure;
    }
}
