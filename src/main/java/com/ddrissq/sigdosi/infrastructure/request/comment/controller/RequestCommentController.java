package com.ddrissq.sigdosi.infrastructure.request.comment.controller;

import com.ddrissq.sigdosi.infrastructure.request.comment.dto.RequestCommentCreateRequest;
import com.ddrissq.sigdosi.infrastructure.request.comment.dto.RequestCommentResponse;
import com.ddrissq.sigdosi.infrastructure.request.comment.dto.RequestCommentUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.request.comment.service.RequestCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(path = "/v1/requests/{requestId}/comments")
@RestController
public class RequestCommentController {

    private final RequestCommentService service;

    @GetMapping
    public ResponseEntity<List<RequestCommentResponse>> getByRequestId(
            @PathVariable UUID requestId) {
        List<RequestCommentResponse> response = service.getByRequestAndParentIsNull(requestId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping(path = "/{id}/replies")
    public ResponseEntity<List<RequestCommentResponse>> getReplies(
            @PathVariable UUID requestId,
            @PathVariable UUID id) {
        List<RequestCommentResponse> response = service.getReplies(id, requestId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<RequestCommentResponse> create(
            @PathVariable UUID requestId,
            @RequestBody @Valid RequestCommentCreateRequest request) {
        RequestCommentResponse response = service.create(requestId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<RequestCommentResponse> update(
            @PathVariable UUID requestId,
            @PathVariable UUID id,
            @RequestBody @Valid RequestCommentUpdateRequest request) {
        RequestCommentResponse response = service.update(requestId, id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID requestId,
            @PathVariable UUID id) {
        service.delete(requestId, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

}
