package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.controller;

import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.dto.HealthFacilityImageCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.dto.HealthFacilityImageDeleteRequest;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.dto.HealthFacilityImageResponse;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.service.HealthFacilityImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(path = "/v1/health-facilities/{healthFacilityId}/images")
@RestController
public class HealthFacilityImageController {

    private final HealthFacilityImageService service;

    @GetMapping
    public ResponseEntity<List<HealthFacilityImageResponse>> get(
            @PathVariable UUID healthFacilityId) {
        List<HealthFacilityImageResponse> response = service.get(healthFacilityId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<HealthFacilityImageResponse> create(
            @PathVariable UUID healthFacilityId,
            @ModelAttribute @Valid HealthFacilityImageCreateRequest request) {
        HealthFacilityImageResponse response = service.create(healthFacilityId, request);
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping(path = "/remove")
    public ResponseEntity<Void> delete(
            @PathVariable UUID healthFacilityId,
            @RequestBody @Valid HealthFacilityImageDeleteRequest request) {
        service.delete(healthFacilityId, request);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .build();
    }

}
