package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.controller;

import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto.HealthFacilityCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto.HealthFacilityResponse;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto.HealthFacilitySearchRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.dto.HealthFacilityUpdateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.service.HealthFacilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(path = "/v1/health-facilities")
@RestController
public class HealthFacilityController {

    private final HealthFacilityService service;

    @GetMapping(path = "/{id}")
    public ResponseEntity<HealthFacilityResponse> get(
            @PathVariable UUID id) {
        HealthFacilityResponse response = service.get(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<HealthFacilityResponse> create(
            @RequestBody @Valid HealthFacilityCreateRequest request) {
        HealthFacilityResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<HealthFacilityResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid HealthFacilityUpdateRequest request) {
        HealthFacilityResponse response = service.update(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Page<HealthFacilityResponse>> getAll(
            @Valid HealthFacilitySearchRequest request,
            Pageable pageable) {
        Page<HealthFacilityResponse> response = service.getAll(request, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

}
