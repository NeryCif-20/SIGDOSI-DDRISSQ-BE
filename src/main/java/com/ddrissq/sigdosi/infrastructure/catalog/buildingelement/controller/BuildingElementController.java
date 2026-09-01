package com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.controller;

import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto.BuildingElementCreateRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto.BuildingElementResponse;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto.BuildingElementSearchRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.dto.BuildingElementUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.service.BuildingElementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(path = "/v1/building-elements")
@RestController
public class BuildingElementController {

    private final BuildingElementService service;

    @GetMapping(path = "/{id}")
    public ResponseEntity<BuildingElementResponse> get(
            @PathVariable UUID id) {
        BuildingElementResponse response = service.get(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<BuildingElementResponse> create(
            @RequestBody @Valid BuildingElementCreateRequest request) {
        BuildingElementResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<BuildingElementResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid BuildingElementUpdateRequest request) {
        BuildingElementResponse response = service.update(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Page<BuildingElementResponse>> getAll(
            BuildingElementSearchRequest request, Pageable pageable) {
        Page<BuildingElementResponse> response = service.getAll(request, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

}
