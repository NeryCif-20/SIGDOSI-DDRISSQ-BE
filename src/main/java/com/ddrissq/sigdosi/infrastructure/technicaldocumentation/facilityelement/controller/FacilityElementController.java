package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.controller;

import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.dto.FacilityElementCreateRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.dto.FacilityElementResponse;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.dto.FacilityElementSearchRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.dto.FacilityElementUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.service.FacilityElementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(path = "/v1/facility-elements")
@RestController
public class FacilityElementController {

    private final FacilityElementService service;

    @GetMapping(path = "/{id}")
    public ResponseEntity<FacilityElementResponse> get(
            @PathVariable UUID id) {
        FacilityElementResponse response = service.get(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<FacilityElementResponse> create(
            @RequestBody @Valid FacilityElementCreateRequest request) {
        FacilityElementResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<FacilityElementResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid FacilityElementUpdateRequest request) {
        FacilityElementResponse response = service.update(id, request);
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
    public ResponseEntity<Page<FacilityElementResponse>> getAll(
            FacilityElementSearchRequest request, Pageable pageable) {
        Page<FacilityElementResponse> response = service.getAll(request, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

}
