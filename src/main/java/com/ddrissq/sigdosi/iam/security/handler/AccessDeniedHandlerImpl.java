package com.ddrissq.sigdosi.iam.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;

@RequiredArgsConstructor
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    private final ObjectMapper mapper;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON.toString());
        response.getWriter().write(buildResponse(request.getRequestURI()));
    }

    private String buildResponse(String path) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN,
                SecurityErrorMessages.ACCESS_DENIED);
        detail.setInstance(URI.create(path));
        detail.setTitle("Authorization Error");
        detail.setProperty("error_category", "Auth");
        detail.setProperty("timestamp", Instant.now());
        return mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(detail);
    }

}
