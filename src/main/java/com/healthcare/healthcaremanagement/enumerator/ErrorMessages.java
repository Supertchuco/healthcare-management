package com.healthcare.healthcaremanagement.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum to define error messages.
 */
@AllArgsConstructor
@Getter
public enum ErrorMessages {

    UNEXPECTED_ERROR("Unexpected error"),
    CREATE_EXAM("Erro to create exam registry on database"),
    CREATE_INSTITUTION("Erro to create health care institute registry in database"),
    EXAM_NOT_FOUND("Exam not found in database"),
    INSTITUTE_INSUFFICIENT_PIXEON_BALANCE("Institute with insufficient pixeon balance."),
    INSTITUTE_NOT_FOUND("Institute not found in database"),
    INVALID_CNPJ("Invalid CNPJ"),
    INVALID_CPF("Invalid CPF"),
    INVALID_GENDER("Invalid gender"),
    RETRIEVE_EXAM("Error on retrieve exam from database"),
    ACCESS_DENIED("Access denied"),
    EMAIL_ALREADY_EXIST("Email already in use by other user"),
    INVALID_ROLE("Role not exist"),
    UPDATE_EXAM("Error during update exam in database");

    private String message;
}
