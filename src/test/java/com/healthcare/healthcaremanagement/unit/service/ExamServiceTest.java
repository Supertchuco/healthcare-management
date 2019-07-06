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
@SuppressWarnings("PMD.TooManyMethods")
public class ExamServiceTest extends BaseTest {

    @InjectMocks
    private ExamService examService;

    @Mock
    private ExamRepository examRepository;

    @Mock
    private HealthCareInstitutionService healthcareInstitutionService;

    @Mock
    private ProcedureService procedureService;

    @Mock
    private PhysicianService physicianService;

    @Mock
    private PatientService patientService;

    private HealthCareInstitution healthcareInstitution = new HealthCareInstitution(INSTITUTION_CNPJ, INSTITUTION_NAME);

    private Object[] inputArray;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateExamWithSuccess() {
        doReturn(healthcareInstitution).when(healthcareInstitutionService).findByCNPJ(any());
        doReturn(healthcareInstitution).when(healthcareInstitutionService).chargePixeonValue(Mockito.any(Integer.class),
                Mockito.any(HealthCareInstitution.class));

        doReturn(new Procedure(PROCEDURE_NAME)).when(procedureService).initializeProcedure(Mockito.any(ExamDto.class));
        doReturn(new Physician(PHYSICIAN_CRM, PHYSICIAN_NAME)).when(physicianService).initializePhysician(Mockito.any(ExamDto.class));
        doReturn(new Patient(PATIENT_CPF, PATIENT_NAME, PATIENT_AGE, PATIENT_GENDER)).when(patientService).initializePatient(Mockito.any(ExamDto.class));

        doReturn(new Exam(null, null, null, healthcareInstitution,
                false)).when(examRepository).save(any(Exam.class));

        assertNotNull(examService.createExam(createExamDto()));

        verify(examRepository, times(1)).save(any(Exam.class));
    }

    @Test(expected = HealthCareInstitutionNotFoundException.class)
    public void shouldThrowInstitutionNotFoundExceptionWhenInstitutionWasNotFoundInDatabase() {
        doThrow(HealthCareInstitutionNotFoundException.class).when(healthcareInstitutionService).findByCNPJ(any());
        examService.createExam(createExamDto());
    }

    @Test(expected = HealthCareInstitutionInsufficientPixeonBalanceException.class)
    public void shouldThrowInstitutionInsufficientPixeonBalanceExceptionWhenInstitutionHaveInsufficientPixeonBalance() {
        doReturn(healthcareInstitution).when(healthcareInstitutionService).findByCNPJ(any());
        doThrow(HealthCareInstitutionInsufficientPixeonBalanceException.class).when(healthcareInstitutionService)
                .chargePixeonValue(Mockito.any(Integer.class),
                        Mockito.any(HealthCareInstitution.class));
        examService.createExam(createExamDto());
    }

    @Test(expected = CreateExamException.class)
    public void shouldThrowCreateExamExceptionWhenSomeUnknowErrorHappens() {

        doReturn(healthcareInstitution).when(healthcareInstitutionService).findByCNPJ(any());
        doReturn(healthcareInstitution).when(healthcareInstitutionService).chargePixeonValue(Mockito.any(Integer.class),
                Mockito.any(HealthCareInstitution.class));

        doReturn(new Procedure(PROCEDURE_NAME)).when(procedureService).initializeProcedure(Mockito.any(ExamDto.class));
        doReturn(new Physician(PHYSICIAN_CRM, PHYSICIAN_NAME)).when(physicianService).initializePhysician(Mockito.any(ExamDto.class));
        doReturn(new Patient(PATIENT_CPF, PATIENT_NAME, PATIENT_AGE, PATIENT_GENDER)).when(patientService)
                .initializePatient(Mockito.any(ExamDto.class));

        doThrow(NullPointerException.class).when(examRepository).save(any(Exam.class));

        examService.createExam(createExamDto());
    }

    @Test
    public void shouldCreateExamObjectWithSuccess() {

        doReturn(new Procedure(PROCEDURE_NAME)).when(procedureService).initializeProcedure(Mockito.any(ExamDto.class));
        doReturn(new Physician(PHYSICIAN_CRM, PHYSICIAN_NAME)).when(physicianService).initializePhysician(Mockito.any(ExamDto.class));
        doReturn(new Patient(PATIENT_CPF, PATIENT_NAME, PATIENT_AGE, PATIENT_GENDER)).when(patientService)
                .initializePatient(Mockito.any(ExamDto.class));

        inputArray = new Object[]{createExamDto(), healthcareInstitution, false};
        Exam exam = ReflectionTestUtils.invokeMethod(examService, "createExamObject", inputArray);

        assertEquals(INSTITUTION_NAME, exam.getHealthcareInstitution().getName());
        assertEquals(INSTITUTION_CNPJ, exam.getHealthcareInstitution().getCnpj());

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
        doReturn(new Exam(null, null, null, healthcareInstitution,
                false)).when(examRepository).findById(anyInt());
        doReturn(healthcareInstitution).when(healthcareInstitutionService).findByCNPJ(any());
        assertNotNull(examService.retrieveExam(10));
        verify(examRepository, times(1)).findById(anyInt());
    }

    @Test(expected = ExamNotFoundException.class)
    public void shouldThrowExamNotFoundExceptionWhenTryRetrieveExamAndExamWasNotFound() {
        examService.retrieveExam(99);
    }

    @Test
    public void shouldDeleteExamWithSuccess() {
        doReturn(new Exam(null, null, null, healthcareInstitution,
                false)).when(examRepository).findById(anyInt());
        examService.deleteExam(88);
        verify(examRepository, times(1)).delete(any(Exam.class));
    }

    @Test
    public void shouldUpdateExamWithSuccess() {
        doReturn(new Exam(null, null, null, healthcareInstitution,
                false)).when(examRepository).findById(anyInt());
        doReturn(new Procedure(PROCEDURE_NAME)).when(procedureService).initializeProcedure(Mockito.any(ExamDto.class));
        doReturn(new Physician(PHYSICIAN_CRM, PHYSICIAN_NAME)).when(physicianService).initializePhysician(Mockito.any(ExamDto.class));
        doReturn(new Patient(PATIENT_CPF, PATIENT_NAME, PATIENT_AGE, PATIENT_GENDER)).when(patientService)
                .initializePatient(Mockito.any(ExamDto.class));

        examService.updateExam(createExamDto(), 66);
        verify(examRepository, times(1)).save(any(Exam.class));
    }

    @Test(expected = UpdateExamException.class)
    public void shouldThrowUpdateExamExceptionWhenTryUpdateExamAndSomeUnknowErrorHappens() {
        doReturn(new Exam(null, null, null, healthcareInstitution,
                false)).when(examRepository).findById(anyInt());
        doReturn(new Procedure(PROCEDURE_NAME)).when(procedureService).initializeProcedure(Mockito.any(ExamDto.class));
        doReturn(new Physician(PHYSICIAN_CRM, PHYSICIAN_NAME)).when(physicianService).initializePhysician(Mockito.any(ExamDto.class));
        doReturn(new Patient(PATIENT_CPF, PATIENT_NAME, PATIENT_AGE, PATIENT_GENDER)).when(patientService)
                .initializePatient(Mockito.any(ExamDto.class));
        doThrow(NullPointerException.class).when(examRepository).save(any(Exam.class));

        examService.updateExam(createExamDto(), 66);
    }

    @Test(expected = ExamNotFoundException.class)
    public void shouldThrowExamNotFoundExceptionWhenTryUpdateExamAndExamWasNotFound() {
        examService.updateExam(createExamDto(), 66);
    }

    @Test(expected = ExamNotFoundException.class)
    public void shouldThrowExamNotFoundExceptionWhenTryDeleteExamAndExamWasNotFound() {
        examService.deleteExam(78);
    }

    @Test
    public void shouldFindExamByIdWithSuccess() {
        doReturn(new Exam(null, null, null, healthcareInstitution,
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
        inputArray = new Object[]{new Exam(null, null, null, healthcareInstitution,
                true)};
        ReflectionTestUtils.invokeMethod(examService, "updateExamRetrievedProcess", inputArray);
        verifyZeroInteractions(examRepository);
        verifyZeroInteractions(healthcareInstitutionService);
    }

    @Test
    public void shouldUpdateExamRetrievedProcessWhenExamWasNotRetrieved() {
        doReturn(new Exam(null, null, null, healthcareInstitution,
                false)).when(examRepository).findById(anyInt());
        inputArray = new Object[]{new Exam(null, null, null, healthcareInstitution,
                false)};
        ReflectionTestUtils.invokeMethod(examService, "updateExamRetrievedProcess", inputArray);
        verify(examRepository, times(1)).save(any(Exam.class));
    }
}
