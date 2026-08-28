package com.ddrissq.sigdosi.healthcarenetwork.dms.controller;

import com.ddrissq.sigdosi.healthcarenetwork.dms.dto.DmsCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.dms.dto.DmsResponse;
import com.ddrissq.sigdosi.healthcarenetwork.dms.dto.DmsSearchRequest;
import com.ddrissq.sigdosi.healthcarenetwork.dms.dto.DmsUpdateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.dms.service.DmsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(path = "/v1/dms")
@RestController
public class DmsController {

    private final DmsService service;

    @GetMapping(path = "/{id}")
    public ResponseEntity<DmsResponse> get(
            @PathVariable UUID id) {
        DmsResponse response = service.get(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<DmsResponse> create(
            @RequestBody DmsCreateRequest request) {
        DmsResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<DmsResponse> patch(
            @PathVariable UUID id,
            @RequestBody @Valid DmsUpdateRequest request) {
        DmsResponse response = service.update(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Page<DmsResponse>> getAll(
            DmsSearchRequest request,
            Pageable pageable) {
        Page<DmsResponse> response = service.getAll(request, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

}
