package com.healthcare.healthcaremanagement.service;

import com.healthcare.healthcaremanagement.dto.ExamDto;
import com.healthcare.healthcaremanagement.entity.Exam;
import com.healthcare.healthcaremanagement.entity.Institution;
import com.healthcare.healthcaremanagement.exception.ExamNotFoundException;
import com.healthcare.healthcaremanagement.repository.ExamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class ExamService {

    private static final int CREATION_EXAM_PIXEON_PRICE = 1;

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

    @Autowired
    private UserService userService;

    @Transactional
    public Exam createExam(final ExamDto examDto) {
        log.info("Create new exam registry");
        Institution institution = institutionService.findByCNPJ(examDto.getInstitutionCNPJ());
        userService.validateUserAccess(institution);
        institution = institutionService.chargePixeonValue(CREATION_EXAM_PIXEON_PRICE, institution);
        final Exam exam = createExamObject(examDto, institution, false);
        return examRepository.save(exam);
    }

    private Exam createExamObject(final ExamDto examDto, final Institution institution,
                                  final boolean retrieved) {
        return new Exam(procedureService.initializeProcedure(examDto),
                patientService.initializePatient(examDto),
                physicianService.initializePhysician(examDto),
                institution,
                retrieved);
    }

    @Transactional
    public Exam retrieveExam(final int examId) {
        log.info("Retrieve exam with id {}", examId);
        final Exam exam = findExamById(examId);
        userService.validateUserAccess(exam.getInstitution());
        updateExamRetrievedProcess(exam);
        return exam;
    }

    public void deleteExam(final int examId) {
        log.info("Delete exam with id {}", examId);
        final Exam exam = findExamById(examId);
        userService.validateUserAccess(exam.getInstitution());
        examRepository.delete(exam);
    }

    @Transactional
    public Exam updateExam(final int examId, final ExamDto examDto) {
        log.info("Update exam with id {}", examId);
        final Exam examDatabase = findExamById(examId);
        userService.validateUserAccess(examDatabase.getInstitution());
        final Institution institution = institutionService
                .findByCNPJ(examDto.getInstitutionCNPJ());
        final Exam exam = createExamObject(examDto, institution,
                examDatabase.isRetrieved());
        exam.setId(examId);
        return examRepository.save(exam);
    }

    private Exam findExamById(final int examId) {
        final Exam exam = examRepository.findById(examId);
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

            final Institution institution = institutionService.findByCNPJ(exam.getInstitution().getCnpj());
            institutionService.chargePixeonValue(CREATION_EXAM_PIXEON_PRICE, institution);

            log.info("Update exam retrieved status on database");
            exam.setRetrieved(true);
            examRepository.save(exam);
        }
    }
}
