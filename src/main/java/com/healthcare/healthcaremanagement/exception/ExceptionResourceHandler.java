package com.healthcare.healthcaremanagement.exception;

import com.healthcare.healthcaremanagement.enumerator.ErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Slf4j
@ControllerAdvice
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class ExceptionResourceHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(final Exception exception, final WebRequest request) {
        log.error("Unexpected error", exception);
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessages.UNEXPECTED_ERROR.getMessage(),
            request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CreateExamException.class)
    public final ResponseEntity<ExceptionResponse> handleCreateExamException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessages.CREATE_EXAM.getMessage(),
            request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CreateHealthCareInstitutionException.class)
    public final ResponseEntity<ExceptionResponse> handleCreateHealthCareInstitutionException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessages.CREATE_INSTITUTION.getMessage(),
            request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExamNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleExamNotFoundException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessages.EXAM_NOT_FOUND.getMessage(),
            request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InstitutionInsufficientPixeonBalanceException.class)
    public final ResponseEntity<ExceptionResponse> handleInstitutionInsufficientPixeonBalanceException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
            ErrorMessages.INSTITUTE_INSUFFICIENT_PIXEON_BALANCE.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InstitutionNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleInstitutionNotFoundException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessages.INSTITUTE_NOT_FOUND.getMessage(),
            request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCNPJException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidCNPJException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessages.INVALID_CNPJ.getMessage(),
            request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCPFException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidCPFException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessages.INVALID_CPF.getMessage(),
            request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidGenderException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidGenderException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessages.INVALID_GENDER.getMessage(),
            request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RetrieveExamException.class)
    public final ResponseEntity<ExceptionResponse> handleRetrieveExamException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessages.RETRIEVE_EXAM.getMessage(),
            request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UpdateExamException.class)
    public final ResponseEntity<ExceptionResponse> handleUpdateExamException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessages.UPDATE_EXAM.getMessage(),
            request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<ExceptionResponse> handleAccessDeniedException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessages.ACCESS_DENIED.getMessage(),
            request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EmailAlreadyExistOnDatabaseException.class)
    public final ResponseEntity<ExceptionResponse> handleEmailAlreadyExistOnDatabaseException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessages.ACCESS_DENIED.getMessage(),
            request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRoleException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidRoleException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessages.INVALID_ROLE.getMessage(),
            request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CNPJAlreadyExistOnDatabaseException.class)
    public final ResponseEntity<ExceptionResponse> handleCNPJAlreadyExistOnDatabaseException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessages.CNPJ_ALREADY_EXIST.getMessage(),
            request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
