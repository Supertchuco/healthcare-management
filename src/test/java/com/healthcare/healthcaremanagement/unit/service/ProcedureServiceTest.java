package com.healthcare.healthcaremanagement.unit.service;

import com.healthcare.healthcaremanagement.entity.Procedure;
import com.healthcare.healthcaremanagement.repository.ProcedureRepository;
import com.healthcare.healthcaremanagement.service.ProcedureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

public class ProcedureServiceTest extends BaseTest {

    @InjectMocks
    private ProcedureService procedureService;

    @Mock
    private ProcedureRepository procedureRepository;

    private Procedure procedure = new Procedure(PROCEDURE_NAME);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldInitializeProcedureWithSuccessWhenProcedureExistOnDatabase() {
        doReturn(procedure).when(procedureRepository).findByName(anyString());
        final Procedure procedureResult = procedureService.initializeProcedure(createExamDto());
        assertEquals(PROCEDURE_NAME, procedureResult.getName());
    }

    @Test
    public void shouldInitializeProcedureWithSuccessWhenProcedureNotExistOnDatabase() {
        final Procedure procedureResult = procedureService.initializeProcedure(createExamDto());
        assertEquals(PROCEDURE_NAME, procedureResult.getName());
    }
}
