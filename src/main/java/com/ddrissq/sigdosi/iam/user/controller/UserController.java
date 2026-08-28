package com.ddrissq.sigdosi.iam.user.controller;

import com.ddrissq.sigdosi.iam.user.dto.*;
import com.ddrissq.sigdosi.iam.user.service.UserService;
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
@RequestMapping(path = "/v1/users")
@RestController
public class UserController {

    private final UserService service;

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserResponse> get(
            @PathVariable UUID id) {
        UserResponse response = service.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(
            @RequestBody @Valid UserCreateRequest request) {
        UserResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<UserResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid UserUpdateRequest request) {
        UserResponse response = service.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping(path = "/{id}/role")
    public ResponseEntity<UserResponse> updateRole(
            @PathVariable UUID id,
            @RequestBody @Valid UserRoleUpdateRequest request) {
        UserResponse response = service.updateRole(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping(path = "/{id}/status")
    public ResponseEntity<UserResponse> updateStatus(
            @PathVariable UUID id,
            @RequestBody @Valid UserStatusUpdateRequest request) {
        UserResponse response = service.updateStatus(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAll(
            UserSearchRequest request,
            @PageableDefault Pageable pageable) {
        Page<UserResponse> response = service.getAll(request, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping(path = "/me")
    public ResponseEntity<UserResponse> update(
            @RequestBody @Valid UserUpdateRequest request) {
        UserResponse response = service.update(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping(path = "/me/avatar")
    public ResponseEntity<UserResponse> updateAvatar(
            @ModelAttribute @Valid UserAvatarUpdateRequest request) {
        UserResponse response = service.updateAvatar(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping(path = "/me/password")
    public ResponseEntity<Void> updatePassword(
            @RequestBody @Valid UserPasswordUpdateRequest request) {
        service.updatePassword(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
