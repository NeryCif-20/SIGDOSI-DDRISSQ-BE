package com.ddrissq.sigdosi.common.handler;

import com.ddrissq.sigdosi.common.constant.ErrorMessageKeys;
import com.ddrissq.sigdosi.common.exception.EntityAlreadyExistsException;
import com.ddrissq.sigdosi.common.exception.EntityNotFoundException;
import com.ddrissq.sigdosi.common.exception.EntityValidationException;
import com.ddrissq.sigdosi.common.file.exception.FileNotFoundException;
import com.ddrissq.sigdosi.common.file.exception.FileStorageException;
import com.ddrissq.sigdosi.common.file.exception.InvalidFileException;
import com.ddrissq.sigdosi.common.message.service.MessageService;
import com.ddrissq.sigdosi.iam.exception.AuthenticationException;
import com.ddrissq.sigdosi.iam.exception.AuthorizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageService messageService;

    @ExceptionHandler(exception = AuthenticationException.class)
    ProblemDetail handleAuthenticationException(AuthenticationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                messageService.getMessage(ex.getMessage()));
        problemDetail.setTitle("Authentication Error");
        problemDetail.setProperty("error_category", "Auth");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(exception = AuthorizationException.class)
    ProblemDetail handleAuthorizationException(AuthorizationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN,
                messageService.getMessage(ex.getMessage()));
        problemDetail.setTitle("Authorization Error");
        problemDetail.setProperty("error_category", "Auth");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(exception = FileNotFoundException.class)
    ProblemDetail handleFileNotFoundException(FileNotFoundException ex) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                messageService.getMessage(ex.getMessage()));
        detail.setTitle("Error Server");
        detail.setProperty("error_category", "Generic");
        detail.setProperty("timestamp", Instant.now());
        return detail;
    }

    @ExceptionHandler(exception = FileStorageException.class)
    ProblemDetail handleFileStorageException(FileStorageException ex) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                messageService.getMessage(ex.getMessage()));
        detail.setTitle("Error Server");
        detail.setProperty("error_category", "Generic");
        detail.setProperty("timestamp", Instant.now());
        return detail;
    }

    @ExceptionHandler(exception = InvalidFileException.class)
    ProblemDetail handleInvalidFileException(InvalidFileException ex) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                messageService.getMessage(ex.getMessage()));
        detail.setTitle("Error Server");
        detail.setProperty("error_category", "Generic");
        detail.setProperty("timestamp", Instant.now());
        return detail;
    }

    @ExceptionHandler(exception = EntityNotFoundException.class)
    ProblemDetail handleEntityNotFoundException(EntityNotFoundException ex) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                messageService.getMessage(ex.getMessage()));
        detail.setTitle("Entity Not Found");
        detail.setProperty("error_category", "Generic");
        detail.setProperty("timestamp", Instant.now());
        return detail;
    }

    @ExceptionHandler(exception = EntityAlreadyExistsException.class)
    ProblemDetail handleEntityAlreadyExistsException(EntityAlreadyExistsException ex) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                messageService.getMessage(ex.getMessage()));
        detail.setTitle("Entity Duplicated");
        detail.setProperty("error_category", "Data Conflict");
        detail.setProperty("timestamp", Instant.now());
        return detail;
    }

    @ExceptionHandler(exception = EntityValidationException.class)
    ProblemDetail handleEntityValidationException(EntityValidationException ex) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                messageService.getMessage(ex.getMessage(), ex.getArgs()));
        detail.setTitle("Entity Validation Error");
        detail.setProperty("error_category", "Data");
        detail.setProperty("timestamp", Instant.now());
        return detail;
    }

    @ExceptionHandler(exception = MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                messageService.getMessage(
                        ErrorMessageKeys.REQUEST_PARAMETER_INVALID));
        detail.setTitle("Invalid Parameter");
        detail.setProperty("parameter", ex.getPropertyName());
        detail.setProperty("error_category", "Validation");
        detail.setProperty("timestamp", Instant.now());
        return detail;
    }

    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                messageService.getMessage(
                        ErrorMessageKeys.REQUEST_VALIDATION_FAILED));
        detail.setTitle("Validation Failed");
        detail.setProperty("error_category", "Validation");
        detail.setProperty("timestamp", Instant.now());
        detail.setProperty("errors", errors);
        return detail;
    }

    @ExceptionHandler(exception = HttpMessageNotReadableException.class)
    public ProblemDetail handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                messageService.getMessage(
                        ErrorMessageKeys.REQUEST_BODY_INVALID));
        problemDetail.setTitle("Invalid Request Body");
        problemDetail.setProperty("error_category", "Validation");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(exception = HttpRequestMethodNotSupportedException.class)
    public ProblemDetail handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.METHOD_NOT_ALLOWED,
                messageService.getMessage(
                        ErrorMessageKeys.REQUEST_METHOD_NOT_ALLOWED));
        problemDetail.setTitle("Method Not Allowed");
        problemDetail.setProperty("error_category", "Generic");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

}
