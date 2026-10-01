package com.ddrissq.sigdosi.common.handler;

import com.ddrissq.sigdosi.common.error.ErrorDescriptor;
import com.ddrissq.sigdosi.common.error.HttpErrorDescriptor;
import com.ddrissq.sigdosi.common.exception.BusinessRuleException;
import com.ddrissq.sigdosi.common.exception.ResourceAlreadyExistsException;
import com.ddrissq.sigdosi.common.exception.ResourceNotFoundException;
import com.ddrissq.sigdosi.common.file.storage.exception.StorageOperationException;
import com.ddrissq.sigdosi.common.hash.exception.HashGenerationException;
import com.ddrissq.sigdosi.common.message.service.MessageService;
import com.ddrissq.sigdosi.iam.exception.AuthenticationException;
import com.ddrissq.sigdosi.iam.exception.AuthorizationException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import tools.jackson.databind.DatabindException;
import tools.jackson.databind.exc.InvalidFormatException;
import tools.jackson.databind.exc.MismatchedInputException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageService messageService;

    @ExceptionHandler(exception = AuthenticationException.class)
    public ProblemDetail handleAuthenticationException(AuthenticationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                messageService.getMessage(ex.getMessage()));
        problemDetail.setTitle(messageService.getMessage(ex.getTitle()));
        problemDetail.setProperty("code", ex.getCode());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(exception = AuthorizationException.class)
    public ProblemDetail handleAuthorizationException(AuthorizationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN,
                messageService.getMessage(ex.getMessage()));
        problemDetail.setTitle(messageService.getMessage(ex.getTitle()));
        problemDetail.setProperty("code", ex.getCode());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(exception = ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException ex) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                messageService.getMessage(ex.getMessage()));
        detail.setTitle(messageService.getMessage(ex.getTitle()));
        detail.setProperty("code", ex.getCode());
        detail.setProperty("timestamp", Instant.now());
        return detail;
    }

    @ExceptionHandler(exception = ResourceAlreadyExistsException.class)
    public ProblemDetail handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                messageService.getMessage(ex.getMessage()));
        detail.setTitle(messageService.getMessage(ex.getTitle()));
        detail.setProperty("code", ex.getCode());
        detail.setProperty("timestamp", Instant.now());
        return detail;
    }

    @ExceptionHandler(exception = BusinessRuleException.class)
    public ProblemDetail handleBusinessException(BusinessRuleException ex) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                messageService.getMessage(ex.getMessage(), ex.getArguments()));
        detail.setTitle(messageService.getMessage(ex.getTitle()));
        detail.setProperty("code", ex.getCode());
        detail.setProperty("timestamp", Instant.now());
        return detail;
    }

    @ExceptionHandler(exception = StorageOperationException.class)
    public ProblemDetail handleFileStorageException(StorageOperationException ex) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                messageService.getMessage(ex.getMessage()));
        detail.setTitle(messageService.getMessage(ex.getTitle()));
        detail.setProperty("code", ex.getCode());
        detail.setProperty("timestamp", Instant.now());
        return detail;
    }

    @ExceptionHandler(exception = HashGenerationException.class)
    public ProblemDetail handleHashGenerationException(HashGenerationException ex) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                messageService.getMessage(ex.getMessage()));
        detail.setTitle(messageService.getMessage(ex.getTitle()));
        detail.setProperty("code", ex.getCode());
        detail.setProperty("timestamp", Instant.now());
        return detail;
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleNoResourceFoundException(
            NoResourceFoundException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ErrorDescriptor descriptor = HttpErrorDescriptor.REQUEST_RESOURCE_NOT_FOUND;
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                messageService.getMessage(descriptor.messageKey(), ex.getResourcePath()));
        detail.setTitle(messageService.getMessage(descriptor.titleKey()));
        detail.setProperty("code", descriptor.code());
        detail.setProperty("timestamp", Instant.now());
        return super.handleExceptionInternal(ex, detail, headers, status, request);
    }

    @ExceptionHandler(exception = MissingRequestCookieException.class)
    public ProblemDetail handleMissingRequestCookieException(MissingRequestCookieException ex) {
        ErrorDescriptor descriptor = HttpErrorDescriptor.REQUEST_COOKIE_REQUIRED;
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                messageService.getMessage(descriptor.messageKey(), ex.getCookieName()));
        detail.setTitle(messageService.getMessage(descriptor.titleKey()));
        detail.setProperty("code", descriptor.code());
        detail.setProperty("timestamp", Instant.now());
        return detail;
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMissingPathVariable(
            MissingPathVariableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ErrorDescriptor descriptor = HttpErrorDescriptor.REQUEST_PATH_VARIABLE_REQUIRED;
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                messageService.getMessage(descriptor.messageKey(), ex.getVariableName()));
        detail.setTitle(messageService.getMessage(descriptor.titleKey()));
        detail.setProperty("code", descriptor.code());
        detail.setProperty("timestamp", Instant.now());
        return super.handleExceptionInternal(ex, detail, headers, status, request);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ErrorDescriptor descriptor = HttpErrorDescriptor.REQUEST_METHOD_ARGUMENT_TYPE_MISMATCHED;
        String name = ex instanceof MethodArgumentTypeMismatchException exception
                ? exception.getName()
                : ex.getPropertyName();
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                messageService.getMessage(descriptor.messageKey(), ex.getValue(), name));
        detail.setTitle(messageService.getMessage(descriptor.titleKey()));
        detail.setProperty("code", descriptor.code());
        detail.setProperty("timestamp", Instant.now());
        return super.handleExceptionInternal(ex, detail, headers, status, request);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        Throwable cause =  ex.getCause();
        List<Object> arguments = new ArrayList<>();
        ErrorDescriptor descriptor = switch (cause) {
            case null -> HttpErrorDescriptor.REQUEST_BODY_REQUIRED;
            case InvalidFormatException exception -> {
                arguments.add(exception.getValue());
                arguments.add(exception.getPath().getLast().getPropertyName());
                yield HttpErrorDescriptor.REQUEST_BODY_TYPE_MISMATCHED;
            }
            case MismatchedInputException exception -> {
                arguments.add(exception.getPath().getLast().getPropertyName());
                yield HttpErrorDescriptor.REQUEST_BODY_STRUCTURE_MISMATCHED;
            }
            case DatabindException exception -> {
                arguments.add(exception.getPath().getLast().getPropertyName());
                yield HttpErrorDescriptor.REQUEST_BODY_DATA_BINDING_FAILED;
            }
            default -> HttpErrorDescriptor.REQUEST_BODY_MALFORMED;
        };
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                messageService.getMessage(descriptor.messageKey(), arguments.toArray()));
        detail.setTitle(messageService.getMessage(descriptor.titleKey()));
        detail.setProperty("code", descriptor.code());
        detail.setProperty("timestamp", Instant.now());
        return super.handleExceptionInternal(ex, detail, headers, status, request);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ErrorDescriptor descriptor = HttpErrorDescriptor.REQUEST_VALIDATION_FAILED;
        Map<String, List<String>> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(
                                FieldError::getDefaultMessage,
                                Collectors.toList())));
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                messageService.getMessage(descriptor.messageKey()));
        detail.setTitle(messageService.getMessage(descriptor.titleKey()));
        detail.setProperty("code", descriptor.code());
        detail.setProperty("timestamp", Instant.now());
        detail.setProperty("errors", errors);
        return super.handleExceptionInternal(ex, detail, headers, status, request);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ErrorDescriptor descriptor = HttpErrorDescriptor.REQUEST_METHOD_NOT_ALLOWED;
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.METHOD_NOT_ALLOWED,
                messageService.getMessage(descriptor.messageKey(), ex.getMethod()));
        detail.setTitle(messageService.getMessage(descriptor.titleKey()));
        detail.setProperty("code", descriptor.code());
        detail.setProperty("timestamp", Instant.now());
        detail.setProperty("supported", ex.getSupportedHttpMethods());
        return super.handleExceptionInternal(ex, detail, headers, status, request);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ErrorDescriptor descriptor = HttpErrorDescriptor.REQUEST_MEDIA_TYPE_UNSUPPORTED;
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                messageService.getMessage(descriptor.messageKey()));
        detail.setTitle(messageService.getMessage(descriptor.titleKey()));
        detail.setProperty("code", descriptor.code());
        detail.setProperty("timestamp", Instant.now());
        detail.setProperty("unsupported", ex.getContentType());
        detail.setProperty("supported", ex.getSupportedMediaTypes());
        return super.handleExceptionInternal(ex, detail, headers, status, request);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
            HttpMediaTypeNotAcceptableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ErrorDescriptor descriptor = HttpErrorDescriptor.REQUEST_MEDIA_TYPE_UNACCEPTED;
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_ACCEPTABLE,
                messageService.getMessage(descriptor.messageKey()));
        detail.setTitle(messageService.getMessage(descriptor.titleKey()));
        detail.setProperty("code", descriptor.code());
        detail.setProperty("timestamp", Instant.now());
        detail.setProperty("accepted", ex.getHeaders().getAccept());
        detail.setProperty("supported", ex.getSupportedMediaTypes());
        return super.handleExceptionInternal(ex, detail, headers, status, request);
    }

}
