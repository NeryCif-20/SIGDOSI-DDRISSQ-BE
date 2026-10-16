package com.ddrissq.sigdosi.infrastructure.request.element.controller;

import com.ddrissq.sigdosi.infrastructure.request.element.dto.RequestElementCreateRequest;
import com.ddrissq.sigdosi.infrastructure.request.element.dto.RequestElementResponse;
import com.ddrissq.sigdosi.infrastructure.request.element.dto.RequestElementUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.request.element.service.RequestElementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(path = "/v1/requests/{requestId}/elements")
@RestController
public class RequestElementController {

    private final RequestElementService service;

    @GetMapping(path = "/{id}")
    public ResponseEntity<RequestElementResponse> get(
            @PathVariable UUID requestId,
            @PathVariable UUID id) {
        RequestElementResponse response = service.get(requestId, id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<RequestElementResponse>> getAll(
            @PathVariable UUID requestId) {
        List<RequestElementResponse> response = service.getAll(requestId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<RequestElementResponse> create(
            @PathVariable UUID requestId,
            @RequestBody @Valid RequestElementCreateRequest request) {
        RequestElementResponse response = service.create(requestId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<RequestElementResponse> update(
            @PathVariable UUID requestId,
            @PathVariable UUID id,
            @RequestBody @Valid RequestElementUpdateRequest request) {
        RequestElementResponse response = service.update(requestId, id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID requestId,
            @PathVariable UUID id) {
        service.delete(requestId, id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .build();
    }

}
