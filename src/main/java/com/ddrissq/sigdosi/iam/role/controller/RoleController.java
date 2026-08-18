package com.ddrissq.sigdosi.iam.role.controller;

import com.ddrissq.sigdosi.iam.role.dto.RoleCreateRequest;
import com.ddrissq.sigdosi.iam.role.dto.RoleResponse;
import com.ddrissq.sigdosi.iam.role.dto.RoleSearchRequest;
import com.ddrissq.sigdosi.iam.role.dto.RoleUpdateRequest;
import com.ddrissq.sigdosi.iam.role.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(path = "/v1/roles")
@RestController
public class RoleController {

    private final RoleService service;

    @GetMapping(path = "/{id}")
    public ResponseEntity<RoleResponse> get(
            @PathVariable UUID id) {
        RoleResponse response = service.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<RoleResponse> create(
            @RequestBody @Valid RoleCreateRequest request) {
        RoleResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<RoleResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid RoleUpdateRequest request) {
        RoleResponse response = service.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<RoleResponse>> getAll(
            RoleSearchRequest request,
            Pageable pageable) {
        Page<RoleResponse> response = service.getAll(request, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
