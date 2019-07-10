package com.healthcare.healthcaremanagement.unit.service;

import com.healthcare.healthcaremanagement.dto.ExamDto;
import com.healthcare.healthcaremanagement.enumerator.Gender;

public class BaseTest {

    public static final String INSTITUTION_CNPJ = "123456789";
    public static final String INSTITUTION_NAME = "institutionTest";
    public static final String PATIENT_NAME = "Etevaldo Silva";
    public static final String PATIENT_CPF = "00309923412";
    public static final int PATIENT_AGE = 56;
    public static final String PATIENT_GENDER = Gender.MALE.name();
    public static final String PHYSICIAN_NAME = "doctor Who";
    public static final String PHYSICIAN_CRM = "12345";
    public static final String PROCEDURE_NAME = "Rectal examination";

    public ExamDto createExamDto() {
        return new ExamDto(INSTITUTION_CNPJ,
            PATIENT_NAME,
            PATIENT_CPF,
            PATIENT_AGE,
            PATIENT_GENDER,
            PHYSICIAN_CRM,
            PHYSICIAN_NAME,
            PROCEDURE_NAME);
    }
}
