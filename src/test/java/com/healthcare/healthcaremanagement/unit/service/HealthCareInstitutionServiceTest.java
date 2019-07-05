package com.healthcare.healthcaremanagement.unit.service;

import com.healthcare.healthcaremanagement.dto.HealthCareInstitutionDto;
import com.healthcare.healthcaremanagement.entity.HealthCareInstitution;
import com.healthcare.healthcaremanagement.exception.CreateHealthCareInstitutionException;
import com.healthcare.healthcaremanagement.exception.HealthCareInstitutionInsufficientPixeonBalanceException;
import com.healthcare.healthcaremanagement.exception.HealthCareInstitutionNotFoundException;
import com.healthcare.healthcaremanagement.exception.InvalidCNPJException;
import com.healthcare.healthcaremanagement.repository.HealthCareInstitutionRepository;
import com.healthcare.healthcaremanagement.service.HealthCareInstitutionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Health Care Institution service unit tests.
 */
@SuppressWarnings("PMD.TooManyMethods")
public class HealthCareInstitutionServiceTest {

    private final static String VALIDATE_CNPJ_HEALTH_CARE_INSTITUTION_METHOD = "validateCNPJHealthCareInstitution";
    private final static String INSTITUTION_NAME = "institutionTest";
    private final static String VALID_CNPJ_1 = "12345678910112";
    private final static String VALID_CNPJ_2 = "22.111.111/9999-00";

    private final static String INVALID_CNPJ_1 = "12345678911";
    private final static String INVALID_CNPJ_2 = "22.111.11C/9999-00";
    private final static String INVALID_CNPJ_3 = "22.111.11C/999-00";

    @InjectMocks
    private HealthCareInstitutionService healthCareInstitutionService;

    @Mock
    private HealthCareInstitutionRepository healthCareInstitutionRepository;

    private HealthCareInstitution healthCareInstitution = new HealthCareInstitution(VALID_CNPJ_1, INSTITUTION_NAME);

    private Object[] inputArray;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateHealthCareInstitutionWithSuccess() {
        doReturn(healthCareInstitution).when(healthCareInstitutionRepository).save(any(HealthCareInstitution.class));
        assertNotNull(healthCareInstitutionService.createHealthCareInstitution(createHealthCareInstitutionDto(VALID_CNPJ_1)));
        verify(healthCareInstitutionRepository, times(1)).save(any(HealthCareInstitution.class));
    }

    @Test(expected = InvalidCNPJException.class)
    public void shouldThrowInvalidCNPJExceptionWhenCreateHealthCareInstitutionWithCNPJShort() {
        healthCareInstitutionService.createHealthCareInstitution(createHealthCareInstitutionDto(INVALID_CNPJ_1));
    }

    @Test(expected = CreateHealthCareInstitutionException.class)
    public void shouldThrowCreateHealthCareInstitutionExceptionWhenCreateHealthCareAndSomeUnknowErrorHappened() {
        doThrow(NullPointerException.class).when(healthCareInstitutionRepository).save(any(HealthCareInstitution.class));
        healthCareInstitutionService.createHealthCareInstitution(createHealthCareInstitutionDto(VALID_CNPJ_1));
    }

    @Test
    public void shouldChargePixeonValueWithSuccess() {
        doReturn(healthCareInstitution).when(healthCareInstitutionRepository).save(any(HealthCareInstitution.class));
        healthCareInstitution.setPixeon(1);
        assertNotNull(healthCareInstitutionService.chargePixeonValue(1, healthCareInstitution));
        verify(healthCareInstitutionRepository, times(1)).save(any(HealthCareInstitution.class));
    }

    @Test(expected = HealthCareInstitutionInsufficientPixeonBalanceException.class)
    public void shouldThrowHealthCareInstitutionInsufficientPixeonBalanceExceptionWhenPixeonValueIsNotEnough() {
        healthCareInstitution.setPixeon(2);
        assertNotNull(healthCareInstitutionService.chargePixeonValue(4, healthCareInstitution));
    }

    @Test
    public void shouldFindHealthCareInstitutionByCNPJWithSuccess() {
        doReturn(healthCareInstitution).when(healthCareInstitutionRepository).findByCnpj(anyString());
        assertNotNull(healthCareInstitutionService.findByCNPJ(VALID_CNPJ_1));
    }

    @Test(expected = HealthCareInstitutionNotFoundException.class)
    public void shouldThrowHealthCareInstitutionNotFoundExceptionWhenHealthCareInstitutionIsNotFoundByCNPJ() {
        healthCareInstitutionService.findByCNPJ(VALID_CNPJ_1);
    }

    @Test
    public void shouldValidateCNPJWithSuccessWithOnlyNumbersInStringInput() {
        inputArray = new Object[]{VALID_CNPJ_1};
        ReflectionTestUtils.invokeMethod(healthCareInstitutionService, VALIDATE_CNPJ_HEALTH_CARE_INSTITUTION_METHOD, inputArray);
    }

    @Test
    public void shouldValidateCNPJWithSuccessWithOnlyNumbersAndDotAndSliceInStringInput() {
        inputArray = new Object[]{VALID_CNPJ_2};
        ReflectionTestUtils.invokeMethod(healthCareInstitutionService, VALIDATE_CNPJ_HEALTH_CARE_INSTITUTION_METHOD, inputArray);
    }

    @Test(expected = InvalidCNPJException.class)
    public void shouldThrowInvalidCNPJExceptionWhenCNPJIsShort() {
        inputArray = new Object[]{INVALID_CNPJ_1};
        ReflectionTestUtils.invokeMethod(healthCareInstitutionService, VALIDATE_CNPJ_HEALTH_CARE_INSTITUTION_METHOD, inputArray);
    }

    @Test(expected = InvalidCNPJException.class)
    public void shouldThrowInvalidCNPJExceptionWhenCNPJIsShortWithDotAndSlice() {
        inputArray = new Object[]{INVALID_CNPJ_3};
        ReflectionTestUtils.invokeMethod(healthCareInstitutionService, VALIDATE_CNPJ_HEALTH_CARE_INSTITUTION_METHOD, inputArray);
    }

    @Test(expected = InvalidCNPJException.class)
    public void shouldThrowInvalidCNPJExceptionWhenCNPJHaveLetter() {
        inputArray = new Object[]{INVALID_CNPJ_2};
        ReflectionTestUtils.invokeMethod(healthCareInstitutionService, VALIDATE_CNPJ_HEALTH_CARE_INSTITUTION_METHOD, inputArray);
    }

    private HealthCareInstitutionDto createHealthCareInstitutionDto(final String cnpj) {
        return new HealthCareInstitutionDto(cnpj, "Crazy House Institute");
    }
}

