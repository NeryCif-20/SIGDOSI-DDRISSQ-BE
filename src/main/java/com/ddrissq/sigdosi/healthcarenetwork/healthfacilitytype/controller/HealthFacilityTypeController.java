package com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.controller;

import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto.HealthFacilityTypeCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto.HealthFacilityTypeResponse;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto.HealthFacilityTypeSearchRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.dto.HealthFacilityTypeUpdateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.service.HealthFacilityTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(path = "/v1/health-facility-types")
@RestController
public class HealthFacilityTypeController {

    private final HealthFacilityTypeService service;

    @GetMapping(path = "/{id}")
    public ResponseEntity<HealthFacilityTypeResponse> get(
            @PathVariable UUID id) {
        HealthFacilityTypeResponse response = service.get(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<HealthFacilityTypeResponse> create(
            @RequestBody @Valid HealthFacilityTypeCreateRequest request) {
        HealthFacilityTypeResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<HealthFacilityTypeResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid HealthFacilityTypeUpdateRequest request) {
        HealthFacilityTypeResponse response = service.update(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Page<HealthFacilityTypeResponse>> getAll(
            HealthFacilityTypeSearchRequest request,
            Pageable pageable) {
        Page<HealthFacilityTypeResponse> response = service.getAll(request, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

}
