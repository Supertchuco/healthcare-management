package com.healthcare.healthcaremanagement.unit.service;

import com.healthcare.healthcaremanagement.dto.ExamDto;
import com.healthcare.healthcaremanagement.enumerator.Gender;

public class BaseTest {

    public final static String INSTITUTION_NAME = "institutionTest";
    public final static String INSTITUTION_CNPJ = "123456789";
    public final static String PATIENT_NAME = "Etevaldo Silva";
    public final static String PATIENT_CPF = "00309923412";
    public final static int PATIENT_AGE = 56;
    public final static String PATIENT_GENDER =  Gender.MALE.name();
    public final static String PHYSICIAN_NAME = "doctor Who";
    public final static String PHYSICIAN_CRM = "12345";
    public final static String PROCEDURE_NAME = "Rectal examination";

    public ExamDto createExamDto() {
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
