package com.ddrissq.sigdosi.iam.security.handler;

import com.ddrissq.sigdosi.iam.constants.IamErrorMessages;
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

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON.toString());
        String detail = resolveDetail(exception.getCause());
        response.getWriter().write(buildResponse(detail, request.getRequestURI()));
    }

    private String resolveDetail(Throwable cause) {
        return switch (cause) {
            case JwtValidationException ex -> IamErrorMessages.EXPIRED_TOKEN;
            case BadJwtException ex -> IamErrorMessages.INVALID_TOKEN;
            default -> IamErrorMessages.AUTHENTICATION_REQUIRED;
        };
    }

    private String buildResponse(String message, String path) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                message);
        detail.setInstance(URI.create(path));
        detail.setTitle("Authentication Error");
        detail.setProperty("error_category", "Auth");
        detail.setProperty("timestamp", Instant.now());
        return mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(detail);
    }

}
