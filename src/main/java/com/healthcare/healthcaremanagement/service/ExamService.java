package com.healthcare.healthcaremanagement.service;

import com.healthcare.healthcaremanagement.dto.ExamDto;
import com.healthcare.healthcaremanagement.entity.Exam;
import com.healthcare.healthcaremanagement.entity.Institution;
import com.healthcare.healthcaremanagement.exception.*;
import com.healthcare.healthcaremanagement.repository.ExamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    @Transactional
    public Exam createExam(final ExamDto examDto) {
        log.info("Create new exam registry");
        try {
            Institution institution = institutionService.findByCNPJ(examDto.getInstitutionCNPJ());
            institution = institutionService.chargePixeonValue(CREATION_EXAM_PIXEON_PRICE, institution);
            final Exam exam = createExamObject(examDto, institution, false);
            return examRepository.save(exam);
        } catch (InstitutionNotFoundException | InstitutionInsufficientPixeonBalanceException ex) {
            throw ex;
        } catch (Exception exception) {
            log.error("Error to create exam", exception);
            throw new CreateExamException();
        }
    }

    private Exam createExamObject(final ExamDto examDto, final Institution healthcareInstitution,
                                  final boolean retrieved) {
        return new Exam(procedureService.initializeProcedure(examDto),
                patientService.initializePatient(examDto),
                physicianService.initializePhysician(examDto),
                healthcareInstitution,
                retrieved);
    }

    @Transactional
    public Exam retrieveExam(final int examId) {
        log.info("Retrieve exam with id {}", examId);
        final Exam exam = findExamById(examId);
        try {
            updateExamRetrievedProcess(exam);
            return exam;
        } catch (InstitutionInsufficientPixeonBalanceException |
                InstitutionNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Unexpected exception in retrieve exam", ex);
            throw new RetrieveExamException();
        }
    }

    public void deleteExam(final int examId) {
        log.info("Delete exam with id {}", examId);
        final Exam exam = findExamById(examId);
        examRepository.delete(exam);
    }

    @Transactional
    public Exam updateExam(final int examId, final ExamDto examDto) {
        log.info("Update exam with id {}", examId);
        final Exam examDatabase = findExamById(examId);
        final Institution institution = institutionService
                .findByCNPJ(examDto.getInstitutionCNPJ());
        try {
            Exam exam = createExamObject(examDto, institution,
                    examDatabase.isRetrieved());
            return examRepository.save(exam);
        } catch (Exception exception) {
            log.error("Error to create exam", exception);
            throw new UpdateExamException();
        }
    }

    private Exam findExamById(final int examId) {
        Exam exam = examRepository.findById(examId);
        if (isNull(exam)) {
            log.error("Exam with id {} not found", examId);
            throw new ExamNotFoundException();
        }
        return exam;
    }

    private void updateExamRetrievedProcess(final Exam exam) {
        log.info("Check if exam retrieved status");
        if (!exam.isRetrieved()) {
            log.info("Exam with id {} was never retrieved, charge Pixeon value");

            Institution institution = institutionService.findByCNPJ(exam.getInstitution().getCnpj());
            institutionService.chargePixeonValue(CREATION_EXAM_PIXEON_PRICE, institution);

            log.info("Update exam retrieved status on database");
            exam.setRetrieved(true);
            examRepository.save(exam);
        }
    }
}
