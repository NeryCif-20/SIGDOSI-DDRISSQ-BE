package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.controller;

import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.dto.PlanCreateRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.dto.PlanResponse;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.dto.PlanSearchRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.dto.PlanUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(path = "/v1/plans")
@RestController
public class PlanController {

    private final PlanService service;

    @GetMapping(path = "/{id}")
    public ResponseEntity<PlanResponse> get(
            @PathVariable UUID id) {
        PlanResponse response = service.get(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<PlanResponse> create(
            @RequestBody @Valid PlanCreateRequest request) {
        PlanResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<PlanResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid PlanUpdateRequest request) {
        PlanResponse response = service.update(id, request);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .build();
    }

    @GetMapping
    public ResponseEntity<Page<PlanResponse>> getAll(
            PlanSearchRequest request,
            Pageable pageable) {
        Page<PlanResponse> response = service.getAll(request, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

}
