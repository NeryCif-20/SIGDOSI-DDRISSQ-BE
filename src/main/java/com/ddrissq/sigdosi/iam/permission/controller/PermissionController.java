package com.ddrissq.sigdosi.iam.permission.controller;

import com.ddrissq.sigdosi.iam.permission.dto.PermissionCreateRequest;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionResponse;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionSearchRequest;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionUpdateRequest;
import com.ddrissq.sigdosi.iam.permission.service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(path = "/v1/permissions")
@RestController
public class PermissionController {

    private final PermissionService service;

    @GetMapping(path = "/{id}")
    public ResponseEntity<PermissionResponse> get(
            @PathVariable UUID id) {
        PermissionResponse response = service.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<PermissionResponse> create(
            @RequestBody @Valid PermissionCreateRequest request) {
        PermissionResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<PermissionResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid PermissionUpdateRequest request) {
        PermissionResponse response = service.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<PermissionResponse>> getAll(
            PermissionSearchRequest request,
            Pageable pageable) {
        Page<PermissionResponse> response = service.getAll(request, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
