package com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.controller;

import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto.BuildingMaterialCreateRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto.BuildingMaterialResponse;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto.BuildingMaterialSearchRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto.BuildingMaterialUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.service.BuildingMaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(path = "/v1/building-materials")
@RestController
public class BuildingMaterialController {

    private final BuildingMaterialService service;

    @GetMapping(path = "/{id}")
    public ResponseEntity<BuildingMaterialResponse> get(
            @PathVariable UUID id) {
        BuildingMaterialResponse response = service.get(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<BuildingMaterialResponse> create(
            @RequestBody @Valid BuildingMaterialCreateRequest request) {
        BuildingMaterialResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<BuildingMaterialResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid BuildingMaterialUpdateRequest request) {
        BuildingMaterialResponse response = service.update(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Page<BuildingMaterialResponse>> getAll(
            BuildingMaterialSearchRequest request,
            Pageable pageable) {
        Page<BuildingMaterialResponse> response = service.getAll(request, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

}
