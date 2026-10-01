package com.ddrissq.sigdosi.iam.security.handler;

import com.ddrissq.sigdosi.common.error.ErrorDescriptor;
import com.ddrissq.sigdosi.common.message.service.MessageService;
import com.ddrissq.sigdosi.iam.error.IamErrorDescriptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;

@RequiredArgsConstructor
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    private final ObjectMapper mapper;
    private final MessageService messageService;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON.toString());
        ErrorDescriptor descriptor = resolveDescriptor(exception.getCause());
        response.getWriter().write(buildResponse(descriptor, request.getRequestURI()));
    }

    private ErrorDescriptor resolveDescriptor(Throwable cause) {
        return switch (cause) {
            case JwtValidationException ex -> IamErrorDescriptor.TOKEN_EXPIRED;
            case BadJwtException ex -> IamErrorDescriptor.TOKEN_INVALID;
            default -> IamErrorDescriptor.AUTHENTICATION_REQUIRED;
        };
    }

    private String buildResponse(ErrorDescriptor descriptor, String path) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                messageService.getMessage(descriptor.messageKey()));
        detail.setInstance(URI.create(path));
        detail.setTitle(messageService.getMessage(descriptor.titleKey()));
        detail.setProperty("code", descriptor.code());
        detail.setProperty("timestamp", Instant.now());
        return mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(detail);
    }

}
