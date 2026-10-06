package com.ddrissq.sigdosi.infrastructure.request.controller;

import com.ddrissq.sigdosi.infrastructure.request.dto.RequestCreateRequest;
import com.ddrissq.sigdosi.infrastructure.request.dto.RequestResponse;
import com.ddrissq.sigdosi.infrastructure.request.dto.RequestSearchRequest;
import com.ddrissq.sigdosi.infrastructure.request.dto.RequestUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.request.service.RequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(path = "/v1/requests")
@RestController
public class RequestController {

    private final RequestService service;

    @GetMapping(path = "/{id}")
    public ResponseEntity<RequestResponse> get(
            @PathVariable UUID id) {
        RequestResponse response = service.get(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<RequestResponse> create(
            @RequestBody @Valid RequestCreateRequest request) {
        RequestResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<RequestResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid RequestUpdateRequest request) {
        RequestResponse response = service.update(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping
    public ResponseEntity<Page<RequestResponse>> getAll(
            @Valid RequestSearchRequest request, Pageable pageable) {
        Page<RequestResponse> response = service.getAll(request, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

}
