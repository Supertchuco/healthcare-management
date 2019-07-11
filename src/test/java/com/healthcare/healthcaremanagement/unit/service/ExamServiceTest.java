package com.healthcare.healthcaremanagement.unit.service;

import com.healthcare.healthcaremanagement.dto.ExamDto;
import com.healthcare.healthcaremanagement.entity.*;
import com.healthcare.healthcaremanagement.exception.*;
import com.healthcare.healthcaremanagement.repository.ExamRepository;
import com.healthcare.healthcaremanagement.service.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Exam service unit tests.
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.UnusedPrivateField"})
public class ExamServiceTest extends BaseTest {

    @InjectMocks
    private ExamService examService;

    @Mock
    private ExamRepository examRepository;

    @Mock
    private UserService userService;

    @Mock
    private InstitutionService institutionService;

    @Mock
    private ProcedureService procedureService;

    @Mock
    private PhysicianService physicianService;

    @Mock
    private PatientService patientService;

    private final Institution institution = new Institution(INSTITUTION_CNPJ, INSTITUTION_NAME);

    private Object[] inputArray;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateExamWithSuccess() {
        doReturn(institution).when(institutionService).findByCNPJ(any());
        doReturn(institution).when(institutionService).chargePixeonValue(Mockito.any(Integer.class),
                Mockito.any(Institution.class));

        doReturn(new Procedure(PROCEDURE_NAME)).when(procedureService).initializeProcedure(Mockito.any(ExamDto.class));
        doReturn(new Physician(PHYSICIAN_CRM, PHYSICIAN_NAME)).when(physicianService)
                .initializePhysician(Mockito.any(ExamDto.class));
        doReturn(new Patient(PATIENT_CPF, PATIENT_NAME, PATIENT_AGE, PATIENT_GENDER)).when(patientService)
                .initializePatient(Mockito.any(ExamDto.class));

        doReturn(new Exam(null, null, null, institution,
                false)).when(examRepository).save(any(Exam.class));

        assertNotNull(examService.createExam(createExamDto()));

        verify(examRepository, times(1)).save(any(Exam.class));
    }

    @Test(expected = InstitutionNotFoundException.class)
    public void shouldThrowInstitutionNotFoundExceptionWhenInstitutionWasNotFoundInDatabase() {
        doThrow(InstitutionNotFoundException.class).when(institutionService).findByCNPJ(any());
        examService.createExam(createExamDto());
    }

    @Test(expected = InstitutionInsufficientPixeonBalanceException.class)
    public void shouldThrowInstitutionInsufficientPixeonBalanceExceptionWhenInstitutionHaveInsufficientPixeonBalance() {
        doReturn(institution).when(institutionService).findByCNPJ(any());
        doThrow(InstitutionInsufficientPixeonBalanceException.class).when(institutionService)
                .chargePixeonValue(Mockito.any(Integer.class),
                        Mockito.any(Institution.class));
        examService.createExam(createExamDto());
    }

    @Test
    public void shouldCreateExamObjectWithSuccess() {

        doReturn(new Procedure(PROCEDURE_NAME)).when(procedureService).initializeProcedure(Mockito.any(ExamDto.class));
        doReturn(new Physician(PHYSICIAN_CRM, PHYSICIAN_NAME)).when(physicianService).initializePhysician(Mockito.any(ExamDto.class));
        doReturn(new Patient(PATIENT_CPF, PATIENT_NAME, PATIENT_AGE, PATIENT_GENDER)).when(patientService)
                .initializePatient(Mockito.any(ExamDto.class));

        inputArray = new Object[]{createExamDto(), institution, false};
        final Exam exam = ReflectionTestUtils.invokeMethod(examService, "createExamObject", inputArray);

        assertEquals(INSTITUTION_NAME, exam.getInstitution().getName());
        assertEquals(INSTITUTION_CNPJ, exam.getInstitution().getCnpj());

        assertEquals(PATIENT_NAME, exam.getPatient().getName());
        assertEquals(PATIENT_CPF, exam.getPatient().getCpf());
        assertEquals(PATIENT_AGE, exam.getPatient().getAge());
        assertEquals(PATIENT_GENDER, exam.getPatient().getGender());

        assertEquals(PHYSICIAN_NAME, exam.getPhysician().getName());
        assertEquals(PHYSICIAN_CRM, exam.getPhysician().getCrm());

        assertEquals(PROCEDURE_NAME, exam.getProcedure().getName());
    }

    @Test
    public void shouldRetrieveExamWithSuccess() {
        doReturn(new Exam(null, null, null, institution,
                false)).when(examRepository).findById(anyInt());
        doReturn(institution).when(institutionService).findByCNPJ(any());
        assertNotNull(examService.retrieveExam(10));
        verify(examRepository, times(1)).findById(anyInt());
    }

    @Test(expected = ExamNotFoundException.class)
    public void shouldThrowExamNotFoundExceptionWhenTryRetrieveExamAndExamWasNotFound() {
        examService.retrieveExam(99);
    }

    @Test
    public void shouldDeleteExamWithSuccess() {
        doReturn(new Exam(null, null, null, institution,
                false)).when(examRepository).findById(anyInt());
        examService.deleteExam(88);
        verify(examRepository, times(1)).delete(any(Exam.class));
    }

    @Test
    public void shouldUpdateExamWithSuccess() {
        doReturn(new Exam(null, null, null, institution,
                false)).when(examRepository).findById(anyInt());
        doReturn(new Procedure(PROCEDURE_NAME)).when(procedureService).initializeProcedure(Mockito.any(ExamDto.class));
        doReturn(new Physician(PHYSICIAN_CRM, PHYSICIAN_NAME)).when(physicianService).initializePhysician(Mockito.any(ExamDto.class));
        doReturn(new Patient(PATIENT_CPF, PATIENT_NAME, PATIENT_AGE, PATIENT_GENDER)).when(patientService)
                .initializePatient(Mockito.any(ExamDto.class));

        examService.updateExam(66, createExamDto());
        verify(examRepository, times(1)).save(any(Exam.class));
    }

    @Test(expected = ExamNotFoundException.class)
    public void shouldThrowExamNotFoundExceptionWhenTryUpdateExamAndExamWasNotFound() {
        examService.updateExam(66, createExamDto());
    }

    @Test(expected = ExamNotFoundException.class)
    public void shouldThrowExamNotFoundExceptionWhenTryDeleteExamAndExamWasNotFound() {
        examService.deleteExam(78);
    }

    @Test
    public void shouldFindExamByIdWithSuccess() {
        doReturn(new Exam(null, null, null, institution,
                false)).when(examRepository).findById(anyInt());
        inputArray = new Object[]{55};
        assertNotNull(ReflectionTestUtils.invokeMethod(examService, "findExamById", inputArray));
    }

    @Test(expected = ExamNotFoundException.class)
    public void shouldThrowExamNotFoundExceptionWhenFindExamByIdAndExamWasNotFound() {
        inputArray = new Object[]{21};
        assertNotNull(ReflectionTestUtils.invokeMethod(examService, "findExamById", inputArray));
    }

    @Test
    public void shouldUpdateExamRetrievedProcessWhenExamWasRetrieved() {
        inputArray = new Object[]{new Exam(null, null, null, institution,
                true)};
        ReflectionTestUtils.invokeMethod(examService, "updateExamRetrievedProcess", inputArray);
        verifyZeroInteractions(examRepository);
        verifyZeroInteractions(institutionService);
    }

    @Test
    public void shouldUpdateExamRetrievedProcessWhenExamWasNotRetrieved() {
        doReturn(new Exam(null, null, null, institution,
                false)).when(examRepository).findById(anyInt());
        inputArray = new Object[]{new Exam(null, null, null, institution,
                false)};
        ReflectionTestUtils.invokeMethod(examService, "updateExamRetrievedProcess", inputArray);
        verify(examRepository, times(1)).save(any(Exam.class));
    }
}
