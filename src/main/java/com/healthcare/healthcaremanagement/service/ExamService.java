package com.healthcare.healthcaremanagement.service;

import com.healthcare.healthcaremanagement.dto.ExamDto;
import com.healthcare.healthcaremanagement.entity.Exam;
import com.healthcare.healthcaremanagement.entity.HealthcareInstitution;
import com.healthcare.healthcaremanagement.exception.CreateExamException;
import com.healthcare.healthcaremanagement.exception.ExamNotFoundException;
import com.healthcare.healthcaremanagement.exception.InstitutionInsufficientPixeonBalanceException;
import com.healthcare.healthcaremanagement.exception.InstitutionNotFoundException;
import com.healthcare.healthcaremanagement.repository.ExamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class ExamService {

    private final static int CREATION_EXAM_PIXEON_PRICE = 1;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PhysicianService physicianService;

    @Autowired
    private ProcedureService procedureService;

    @Autowired
    private InstitutionService institutionService;


    public Exam createExam(final ExamDto examDto) {
        log.info("Create new exam registry");
        try {
            HealthcareInstitution healthcareInstitution = institutionService.findByCnpj(examDto.getInstitutionCnpj());
            healthcareInstitution = institutionService.chargePixeonValue(CREATION_EXAM_PIXEON_PRICE, healthcareInstitution);
            Exam exam = createExamObject(examDto, healthcareInstitution, false);
            return examRepository.save(exam);
        } catch (InstitutionNotFoundException | InstitutionInsufficientPixeonBalanceException ex) {
            throw ex;
        } catch (Exception exception) {
            log.error("Error to create exam", exception);
            throw new CreateExamException();
        }
    }

    private Exam createExamObject(final ExamDto examDto, final HealthcareInstitution healthcareInstitution,
                                 final boolean retrieved) {
        return new Exam(procedureService.initializeProcedure(examDto),
                patientService.initializePatient(examDto),
                physicianService.initializePhysician(examDto),
                healthcareInstitution,
                retrieved);
    }

    public Exam retrieveExam(final int id) {
        log.info("Retrieve exam with id {}", id);
        final Exam exam = examRepository.findById(id);
        if (isNull(exam)) {
            log.error("Exam with id {} not found", id);
            throw new ExamNotFoundException();
        }
        updateExamRetrievedProcess(exam);
        return exam;
    }

    public void deleteExam(final int examId) {
        log.info("Delete exam with id {}", examId);
        Exam exam = examRepository.findById(examId);
        examRepository.delete(exam);
    }

    public Exam updateExam(final ExamDto examDto, final int examId) {
        log.info("Update exam with id {}", examId);
        Exam examDatabase = examRepository.findById(examId);
        try {
            Exam exam = createExamObject(examDto, institutionService.findByCnpj(examDto.getInstitutionCnpj()),
                    examDatabase.isRetrieved());
            return examRepository.save(exam);
        } catch (InstitutionNotFoundException | InstitutionInsufficientPixeonBalanceException ex) {
            throw ex;
        } catch (Exception exception) {
            log.error("Error to create exam", exception);
            throw new CreateExamException();
        }
    }

    private void updateExamRetrievedProcess(final Exam exam) {
        log.info("Check if exam retrieved status");
        if (!exam.isRetrieved()) {
            log.info("Exam with id {} was never retrieved, charge Pixeon value");

            HealthcareInstitution healthcareInstitution = institutionService.findByCnpj(exam.getHealthcareInstitution().getCnpj());
            institutionService.chargePixeonValue(CREATION_EXAM_PIXEON_PRICE, healthcareInstitution);

            log.info("Update exam retrieved status on database");
            exam.setRetrieved(true);
            examRepository.save(exam);
        }
    }


}
