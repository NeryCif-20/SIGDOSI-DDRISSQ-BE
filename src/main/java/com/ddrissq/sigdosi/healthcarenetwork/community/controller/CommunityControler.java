package com.ddrissq.sigdosi.healthcarenetwork.community.controller;

import com.ddrissq.sigdosi.healthcarenetwork.community.dto.CommunityCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.community.dto.CommunityResponse;
import com.ddrissq.sigdosi.healthcarenetwork.community.dto.CommunitySearchRequest;
import com.ddrissq.sigdosi.healthcarenetwork.community.dto.CommunityUpdateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.community.service.CommunityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(path = "/v1/communities")
@RestController
public class CommunityControler {

    private final CommunityService service;

    @GetMapping(path = "/{id}")
    public ResponseEntity<CommunityResponse> get(
            @PathVariable UUID id) {
        CommunityResponse response = service.get(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<CommunityResponse> create(
            @RequestBody @Valid CommunityCreateRequest request) {
        CommunityResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<CommunityResponse> udpate(
            @PathVariable UUID id,
            @RequestBody @Valid CommunityUpdateRequest request) {
        CommunityResponse response = service.update(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Page<CommunityResponse>> getAll(
            CommunitySearchRequest request,
            @PageableDefault Pageable pageable) {
        Page<CommunityResponse> response = service.getAll(request, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

}
