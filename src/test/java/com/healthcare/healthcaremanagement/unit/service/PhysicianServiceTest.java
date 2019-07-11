package com.healthcare.healthcaremanagement.unit.service;

import com.healthcare.healthcaremanagement.entity.Physician;
import com.healthcare.healthcaremanagement.repository.PhysicianRepository;
import com.healthcare.healthcaremanagement.service.PhysicianService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@SuppressWarnings("PMD.ImmutableField")
public class PhysicianServiceTest extends BaseTest {

    @InjectMocks
    private PhysicianService physicianService;

    @Mock
    private PhysicianRepository physicianRepository;

    private Physician physician = new Physician(PHYSICIAN_CRM, PHYSICIAN_NAME);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldInitializePhysicianWithSuccessWhenPhysicianExistOnDatabase() {
        doReturn(physician).when(physicianRepository).findByCrm(anyString());
        final Physician physicianResult = physicianService.initializePhysician(createExamDto());
        assertEquals(PHYSICIAN_CRM, physicianResult.getCrm());
        assertEquals(PHYSICIAN_NAME, physicianResult.getName());
    }

    @Test
    public void shouldInitializePhysicianWithSuccessWhenPhysicianNotExistOnDatabase() {
        final Physician physicianResult = physicianService.initializePhysician(createExamDto());
        assertEquals(PHYSICIAN_CRM, physicianResult.getCrm());
        assertEquals(PHYSICIAN_NAME, physicianResult.getName());
    }
}
