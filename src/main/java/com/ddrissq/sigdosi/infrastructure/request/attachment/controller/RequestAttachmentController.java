package com.ddrissq.sigdosi.infrastructure.request.attachment.controller;

import com.ddrissq.sigdosi.infrastructure.request.attachment.dto.RequestAttachmentCreateRequest;
import com.ddrissq.sigdosi.infrastructure.request.attachment.dto.RequestAttachmentDeleteRequest;
import com.ddrissq.sigdosi.infrastructure.request.attachment.dto.RequestAttachmentResponse;
import com.ddrissq.sigdosi.infrastructure.request.attachment.service.RequestAttachmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(path = "/v1/requests/{requestId}/attachments")
@RestController
public class RequestAttachmentController {

    private final RequestAttachmentService service;

    @GetMapping
    public ResponseEntity<List<RequestAttachmentResponse>> get(
            @PathVariable UUID requestId) {
        List<RequestAttachmentResponse> response = service.get(requestId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<RequestAttachmentResponse> create(
            @PathVariable UUID requestId,
            @RequestBody @Valid RequestAttachmentCreateRequest request) {
        RequestAttachmentResponse response = service.create(requestId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping(path = "/remove")
    public ResponseEntity<Void> delete(
            @PathVariable UUID requestId,
            @RequestBody @Valid RequestAttachmentDeleteRequest request) {
        service.delete(requestId, request);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .build();
    }

}
