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
    private final MessageService messageService;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON.toString());
        response.getWriter().write(buildResponse(request.getRequestURI()));
    }

    private String buildResponse(String path) {
        ErrorDescriptor descriptor = IamErrorDescriptor.ACCESS_DENIED;
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN,
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
