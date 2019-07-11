package com.healthcare.healthcaremanagement.unit.service;

import com.healthcare.healthcaremanagement.dto.InstitutionDto;
import com.healthcare.healthcaremanagement.entity.Institution;
import com.healthcare.healthcaremanagement.exception.CreateHealthCareInstitutionException;
import com.healthcare.healthcaremanagement.exception.InstitutionInsufficientPixeonBalanceException;
import com.healthcare.healthcaremanagement.exception.InstitutionNotFoundException;
import com.healthcare.healthcaremanagement.exception.InvalidCNPJException;
import com.healthcare.healthcaremanagement.repository.InstitutionRepository;
import com.healthcare.healthcaremanagement.service.InstitutionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Health Care Institution service unit tests.
 */
@SuppressWarnings({"PMD.TooManyMethods", "checkstyle:AbbreviationAsWordInName", "PMD.ImmutableField"})
public class InstitutionServiceTest {

    private static final String FORMAT_CNPJ_INSTITUTION_METHOD = "formatCNPJInstitution";
    private static final String INSTITUTION_NAME = "institutionTest";
    private static final String VALID_CNPJ_1 = "12345678910112";
    private static final String VALID_CNPJ_2 = "22.111.111/9999-00";

    private static final String INVALID_CNPJ_1 = "12345678911";
    private static final String INVALID_CNPJ_2 = "22.111.11C/9999-00";
    private static final String INVALID_CNPJ_3 = "22.111.11C/999-00";

    @InjectMocks
    private InstitutionService institutionService;

    @Mock
    private InstitutionRepository institutionRepository;

    private Institution institution = new Institution(VALID_CNPJ_1, INSTITUTION_NAME);

    private Object[] inputArray;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateHealthCareInstitutionWithSuccess() {
        doReturn(institution).when(institutionRepository).save(any(Institution.class));
        assertNotNull(institutionService.createInstitution(createHealthCareInstitutionDto(VALID_CNPJ_1)));
        verify(institutionRepository, times(1)).save(any(Institution.class));
    }

    @Test(expected = InvalidCNPJException.class)
    public void shouldThrowInvalidCNPJExceptionWhenCreateHealthCareInstitutionWithCNPJShort() {
        institutionService.createInstitution(createHealthCareInstitutionDto(INVALID_CNPJ_1));
    }

    @Test(expected = CreateHealthCareInstitutionException.class)
    public void shouldThrowCreateHealthCareInstitutionExceptionWhenCreateHealthCareAndSomeUnknowErrorHappened() {
        doThrow(NullPointerException.class).when(institutionRepository).save(any(Institution.class));
        institutionService.createInstitution(createHealthCareInstitutionDto(VALID_CNPJ_1));
    }

    @Test
    public void shouldChargePixeonValueWithSuccess() {
        doReturn(institution).when(institutionRepository).save(any(Institution.class));
        institution.setPixeon(1);
        assertNotNull(institutionService.chargePixeonValue(1, institution));
        verify(institutionRepository, times(1)).save(any(Institution.class));
    }

    @Test(expected = InstitutionInsufficientPixeonBalanceException.class)
    public void shouldThrowHealthCareInstitutionInsufficientPixeonBalanceExceptionWhenPixeonValueIsNotEnough() {
        institution.setPixeon(2);
        assertNotNull(institutionService.chargePixeonValue(4, institution));
    }

    @Test
    public void shouldFindHealthCareInstitutionByCNPJWithSuccess() {
        doReturn(institution).when(institutionRepository).findByCnpj(anyString());
        assertNotNull(institutionService.findByCNPJ(VALID_CNPJ_1));
    }

    @Test(expected = InstitutionNotFoundException.class)
    public void shouldThrowHealthCareInstitutionNotFoundExceptionWhenHealthCareInstitutionIsNotFoundByCNPJ() {
        institutionService.findByCNPJ(VALID_CNPJ_1);
    }

    @Test
    public void shouldValidateCNPJWithSuccessWithOnlyNumbersInStringInput() {
        inputArray = new Object[]{VALID_CNPJ_1};
        assertEquals(VALID_CNPJ_1, ReflectionTestUtils.invokeMethod(institutionService, FORMAT_CNPJ_INSTITUTION_METHOD, inputArray));
    }

    @Test
    public void shouldValidateCNPJWithSuccessWithOnlyNumbersAndDotAndSliceInStringInput() {
        inputArray = new Object[]{VALID_CNPJ_2};
        assertEquals("22111111999900", ReflectionTestUtils.invokeMethod(institutionService, FORMAT_CNPJ_INSTITUTION_METHOD, inputArray));
    }

    @Test(expected = InvalidCNPJException.class)
    public void shouldThrowInvalidCNPJExceptionWhenCNPJIsShort() {
        inputArray = new Object[]{INVALID_CNPJ_1};
        ReflectionTestUtils.invokeMethod(institutionService, FORMAT_CNPJ_INSTITUTION_METHOD, inputArray);
    }

    @Test(expected = InvalidCNPJException.class)
    public void shouldThrowInvalidCNPJExceptionWhenCNPJIsShortWithDotAndSlice() {
        inputArray = new Object[]{INVALID_CNPJ_3};
        ReflectionTestUtils.invokeMethod(institutionService, FORMAT_CNPJ_INSTITUTION_METHOD, inputArray);
    }

    @Test(expected = InvalidCNPJException.class)
    public void shouldThrowInvalidCNPJExceptionWhenCNPJHaveLetter() {
        inputArray = new Object[]{INVALID_CNPJ_2};
        ReflectionTestUtils.invokeMethod(institutionService, FORMAT_CNPJ_INSTITUTION_METHOD, inputArray);
    }

    private InstitutionDto createHealthCareInstitutionDto(final String cnpj) {
        return new InstitutionDto(cnpj, "Crazy House Institute");
    }
}

