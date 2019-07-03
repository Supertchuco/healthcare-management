package com.healthcare.healthcaremanagement.unit.service;

import com.healthcare.healthcaremanagement.dto.ExamDto;
import com.healthcare.healthcaremanagement.entity.*;
import com.healthcare.healthcaremanagement.exception.CreateExamException;
import com.healthcare.healthcaremanagement.exception.InstitutionInsufficientPixeonBalanceException;
import com.healthcare.healthcaremanagement.exception.InstitutionNotFoundException;
import com.healthcare.healthcaremanagement.repository.ExamRepository;
import com.healthcare.healthcaremanagement.service.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Exam service unit tests.
 */
@SuppressWarnings("PMD.TooManyMethods")
public class ExamServiceTest {

    private final static String INSTITUTION_NAME = "institutionTest";
    private final static String INSTITUTION_CNPJ = "123456789";
    private final static String PATIENT_NAME = "patientTest";
    private final static String PATIENT_CPF = "12345009";
    private final static int PATIENT_AGE = 56;
    private final static String PATIENT_GENDER = "male";
    private final static String PHYSICIAN_NAME = "doctor Who";
    private final static String PHYSICIAN_CRM = "12345";
    private final static String PROCEDURE_NAME = "Rectal examination";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    @Spy
    private ExamService examService;

    @Mock
    private ExamRepository examRepository;

    @Mock
    private InstitutionService institutionService;

    @Mock
    private ProcedureService procedureService;

    @Mock
    private PhysicianService physicianService;

    @Mock
    private PatientService patientService;

    private HealthcareInstitution healthcareInstitution = new HealthcareInstitution(INSTITUTION_CNPJ, INSTITUTION_NAME);

    private Object[] inputArray;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateExamWithSuccess() {

        doReturn(healthcareInstitution).when(institutionService).findByCnpj(any());
        doReturn(healthcareInstitution).when(institutionService).chargePixeonValue(Mockito.any(Integer.class),
                Mockito.any(HealthcareInstitution.class));

        doReturn(new Procedure(PROCEDURE_NAME)).when(procedureService).initializeProcedure(Mockito.any(ExamDto.class));
        doReturn(new Physician(PHYSICIAN_CRM, PHYSICIAN_NAME)).when(physicianService).initializePhysician(Mockito.any(ExamDto.class));
        doReturn(new Patient(PATIENT_CPF, PATIENT_NAME, PATIENT_AGE, PATIENT_GENDER)).when(patientService).initializePatient(Mockito.any(ExamDto.class));

        examService.createExam(createExamDto());

        verify(examRepository, times(1)).save(any(Exam.class));
    }

    @Test(expected = InstitutionNotFoundException.class)
    public void shouldThrowInstitutionNotFoundExceptionWhenInstitutionWasNotFoundInDatabase() {
        doThrow(InstitutionNotFoundException.class).when(institutionService).findByCnpj(any());
        examService.createExam(createExamDto());
    }

    @Test(expected = InstitutionInsufficientPixeonBalanceException.class)
    public void shouldThrowInstitutionInsufficientPixeonBalanceExceptionWhenInstitutionHaveInsufficientPixeonBalance() {
        doReturn(healthcareInstitution).when(institutionService).findByCnpj(any());
        doThrow(InstitutionInsufficientPixeonBalanceException.class).when(institutionService)
                .chargePixeonValue(Mockito.any(Integer.class),
                        Mockito.any(HealthcareInstitution.class));
        examService.createExam(createExamDto());
    }

    @Test(expected = CreateExamException.class)
    public void shouldThrowCreateExamExceptionWhenSomeUnknowErrorOccurred() {

        doReturn(healthcareInstitution).when(institutionService).findByCnpj(any());
        doReturn(healthcareInstitution).when(institutionService).chargePixeonValue(Mockito.any(Integer.class),
                Mockito.any(HealthcareInstitution.class));

        doReturn(new Procedure(PROCEDURE_NAME)).when(procedureService).initializeProcedure(Mockito.any(ExamDto.class));
        doReturn(new Physician(PHYSICIAN_CRM, PHYSICIAN_NAME)).when(physicianService).initializePhysician(Mockito.any(ExamDto.class));
        doReturn(new Patient(PATIENT_CPF, PATIENT_NAME, PATIENT_AGE, PATIENT_GENDER)).when(patientService)
                .initializePatient(Mockito.any(ExamDto.class));

        doThrow(NullPointerException.class).when(examRepository).save(any(Exam.class));

        examService.createExam(createExamDto());
    }

    @Test
    public void shouldCreateExamObjectWithSuccess(){

        doReturn(new Procedure(PROCEDURE_NAME)).when(procedureService).initializeProcedure(Mockito.any(ExamDto.class));
        doReturn(new Physician(PHYSICIAN_CRM, PHYSICIAN_NAME)).when(physicianService).initializePhysician(Mockito.any(ExamDto.class));
        doReturn(new Patient(PATIENT_CPF, PATIENT_NAME, PATIENT_AGE, PATIENT_GENDER)).when(patientService)
                .initializePatient(Mockito.any(ExamDto.class));

        inputArray = new Object[]{createExamDto(), healthcareInstitution, false};
        Exam exam =  ReflectionTestUtils.invokeMethod(examService, "createExamObject", inputArray);

        assertEquals( INSTITUTION_NAME, exam.getHealthcareInstitution().getName());
        assertEquals( INSTITUTION_CNPJ, exam.getHealthcareInstitution().getCnpj());

        assertEquals( PATIENT_NAME, exam.getPatient().getName());
        assertEquals( PATIENT_CPF, exam.getPatient().getCpf());
        assertEquals( PATIENT_AGE, exam.getPatient().getAge());
        assertEquals( PATIENT_GENDER, exam.getPatient().getGender());

        assertEquals( PHYSICIAN_NAME, exam.getPhysician().getName());
        assertEquals( PHYSICIAN_CRM, exam.getPhysician().getCrm());

        assertEquals( PROCEDURE_NAME, exam.getProcedure().getName());
    }


    private ExamDto createExamDto() {
        return new ExamDto(INSTITUTION_NAME,
                INSTITUTION_CNPJ,
                PATIENT_NAME,
                PATIENT_CPF,
                PATIENT_AGE,
                PATIENT_GENDER,
                PHYSICIAN_CRM,
                PHYSICIAN_NAME,
                PROCEDURE_NAME);
    }


}
