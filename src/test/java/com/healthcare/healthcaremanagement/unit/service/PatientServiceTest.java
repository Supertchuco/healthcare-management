package com.healthcare.healthcaremanagement.unit.service;

import com.healthcare.healthcaremanagement.entity.Patient;
import com.healthcare.healthcaremanagement.enumerator.Gender;
import com.healthcare.healthcaremanagement.exception.InvalidCPFException;
import com.healthcare.healthcaremanagement.exception.InvalidGenderException;
import com.healthcare.healthcaremanagement.repository.PatientRepository;
import com.healthcare.healthcaremanagement.service.PatientService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

/**
 * Patient service unit tests.
 */
@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "PMD.ImmutableField"})
public class PatientServiceTest extends BaseTest {

    private static final String FORMAT_CPF_METHOD = "formatCPF";
    private static final String VALIDATE_GENDER_METHOD = "validateGender";

    private static final String VALID_CPF_1 = "357.672.271-87";
    private static final String VALID_CPF_2 = "35767227187";

    private static final String INVALID_CPF_1 = "3576722718";
    private static final String INVALID_CPF_2 = "357.6B2.271-87";
    private static final String INVALID_CPF_3 = "357.672.27-87";

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    private Patient patient = new Patient(PATIENT_CPF, PATIENT_NAME, PATIENT_AGE, PATIENT_GENDER);

    private Object[] inputArray;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldInitializePatientWhenPatientExistOnDatabase() {
        doReturn(patient).when(patientRepository).findByCpf(anyString());
        final Patient patientResult = patientService.initializePatient(createExamDto());
        assertEquals(PATIENT_CPF, patientResult.getCpf());
        assertEquals(PATIENT_NAME, patientResult.getName());
        assertEquals(PATIENT_AGE, patientResult.getAge());
        assertEquals(PATIENT_GENDER, patientResult.getGender());
    }

    @Test
    public void shouldInitializePatientWhenPatientNotExistOnDatabase() {
        final Patient patient = patientService.initializePatient(createExamDto());
        assertEquals(PATIENT_CPF, patient.getCpf());
        assertEquals(PATIENT_NAME, patient.getName());
        assertEquals(PATIENT_AGE, patient.getAge());
        assertEquals(PATIENT_GENDER, patient.getGender());
    }

    @Test
    public void shouldNotThrowInvalidCPFExceptionWhenCPFIsValid() {
        inputArray = new Object[]{VALID_CPF_1};
        assertEquals("35767227187", ReflectionTestUtils.invokeMethod(patientService, FORMAT_CPF_METHOD, inputArray));
    }

    @Test
    public void shouldNotThrowInvalidCPFExceptionWhenCPFHaveOnlyNumbersAndIsValid() {
        inputArray = new Object[]{VALID_CPF_2};
        assertEquals(VALID_CPF_2, ReflectionTestUtils.invokeMethod(patientService, FORMAT_CPF_METHOD, inputArray));
    }

    @Test(expected = InvalidCPFException.class)
    public void shouldThrowInvalidCPFExceptionWhenCNPFIsShort() {
        inputArray = new Object[]{INVALID_CPF_3};
        ReflectionTestUtils.invokeMethod(patientService, FORMAT_CPF_METHOD, inputArray);
    }

    @Test(expected = InvalidCPFException.class)
    public void shouldThrowInvalidCPFExceptionWhenCNPFIsShortWithOnlyNumbers() {
        inputArray = new Object[]{INVALID_CPF_1};
        ReflectionTestUtils.invokeMethod(patientService, FORMAT_CPF_METHOD, inputArray);
    }

    @Test(expected = InvalidCPFException.class)
    public void shouldThrowInvalidCPFExceptionWhenCNPFHaveLetter() {
        inputArray = new Object[]{INVALID_CPF_2};
        ReflectionTestUtils.invokeMethod(patientService, FORMAT_CPF_METHOD, inputArray);
    }

    @Test
    public void shouldNotThrowInvalidGenderExceptionWhenGenderIsValid() {
        inputArray = new Object[]{Gender.FEMALE.name()};
        ReflectionTestUtils.invokeMethod(patientService, VALIDATE_GENDER_METHOD, inputArray);
    }

    @Test(expected = InvalidGenderException.class)
    public void shouldThrowInvalidGenderExceptionWhenGenderIsNotValid() {
        inputArray = new Object[]{"OtherValue"};
        ReflectionTestUtils.invokeMethod(patientService, VALIDATE_GENDER_METHOD, inputArray);
    }
}
