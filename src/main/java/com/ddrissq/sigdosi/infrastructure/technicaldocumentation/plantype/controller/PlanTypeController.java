package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.controller;

import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto.PlanTypeCreateRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto.PlanTypeResponse;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto.PlanTypeSearchRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.dto.PlanTypeUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.service.PlanTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(path = "/v1/plan-types")
@RestController
public class PlanTypeController {

    private final PlanTypeService service;

    @GetMapping(path = "/{id}")
    public ResponseEntity<PlanTypeResponse> get(
            @PathVariable UUID id) {
        PlanTypeResponse response = service.get(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<PlanTypeResponse> create(
            @RequestBody @Valid PlanTypeCreateRequest request) {
        PlanTypeResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<PlanTypeResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid PlanTypeUpdateRequest request) {
        PlanTypeResponse response = service.update(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Page<PlanTypeResponse>> getAll(
            PlanTypeSearchRequest request,
            Pageable pageable) {
        Page<PlanTypeResponse> response = service.getAll(request, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

}
