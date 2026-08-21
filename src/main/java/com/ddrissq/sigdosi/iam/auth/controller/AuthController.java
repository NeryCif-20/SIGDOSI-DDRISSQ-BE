package com.ddrissq.sigdosi.iam.auth.controller;

import com.ddrissq.sigdosi.iam.auth.cookie.constant.AuthCookieNames;
import com.ddrissq.sigdosi.iam.auth.cookie.service.AuthCookieService;
import com.ddrissq.sigdosi.iam.auth.dto.*;
import com.ddrissq.sigdosi.iam.auth.model.AuthIdentityResult;
import com.ddrissq.sigdosi.iam.auth.model.AuthResult;
import com.ddrissq.sigdosi.iam.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(path = "/v1/auth")
@RestController
public class AuthController {

    private final AuthService service;
    private final AuthCookieService cookieService;

    @PostMapping(path = "/identify")
    public ResponseEntity<AuthIdentifyResponse> identify(
            @RequestBody @Valid AuthIdentifyRequest request) {
        AuthIdentityResult result = service.identify(request);
        ResponseCookie flowTokenCookie = cookieService.createFlowTokenCookie(
                result.flowToken(), result.expiresAt());
        AuthIdentifyResponse response = AuthIdentifyResponse.builder()
                .step(result.step())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, flowTokenCookie.toString())
                .body(response);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponse> login(
            @CookieValue(value = AuthCookieNames.FLOW_TOKEN) String token,
            @RequestBody @Valid AuthLoginRequest request) {
        AuthResult result = service.login(token, request);
        ResponseCookie flowTokenCookie = cookieService.deleteFlowTokenCookie();
        ResponseCookie refreshTokenCookie = cookieService.createRefreshTokenCookie(
                result.refreshToken(), result.expiresAt());
        AuthResponse response = AuthResponse.builder()
                .accessToken(result.accessToken())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, flowTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(response);
    }

    @PostMapping(path = "/password/setup")
    public ResponseEntity<Void> setUpPassword(
            @CookieValue(value = AuthCookieNames.FLOW_TOKEN) String token) {
        service.sendSetupPasswordEmail(token);
        ResponseCookie flowTokenCookie = cookieService.deleteFlowTokenCookie();
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .header(HttpHeaders.SET_COOKIE, flowTokenCookie.toString())
                .build();
    }

    @PostMapping(path = "/password/reset")
    public ResponseEntity<Void> resetPassword(
            @CookieValue(value = AuthCookieNames.FLOW_TOKEN) String token) {
        service.sendResetPasswordEmail(token);
        ResponseCookie flowTokenCookie = cookieService.deleteFlowTokenCookie();
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .header(HttpHeaders.SET_COOKIE, flowTokenCookie.toString())
                .build();
    }

    @PostMapping(path = "/refresh")
    public ResponseEntity<AuthResponse> refresh(
            @CookieValue(value = AuthCookieNames.REFRESH_TOKEN) String token) {
        AuthResult result = service.refresh(token);
        ResponseCookie refreshTokenCookie = cookieService.createRefreshTokenCookie(
                result.refreshToken(), result.expiresAt());
        AuthResponse response = AuthResponse.builder()
                .accessToken(result.accessToken())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(response);
    }

    @PostMapping(path = "/password/validate")
    public ResponseEntity<Void> validate(
            @RequestBody @Valid AuthPasswordValidateRequest request) {
        service.validatePasswordToken(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PostMapping(path = "/password/set")
    public ResponseEntity<AuthResponse> setPassword(
            @RequestBody @Valid AuthPasswordSetRequest request) {
        AuthResult result = service.setPassword(request);
        ResponseCookie refreshTokenCookie = cookieService.createRefreshTokenCookie(
                result.refreshToken(), result.expiresAt());
        AuthResponse response = AuthResponse.builder()
                .accessToken(result.accessToken())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(response);
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<Void> logout(
            @CookieValue(value = AuthCookieNames.REFRESH_TOKEN) String token) {
        service.logout(token);
        ResponseCookie refreshTokenCookie = cookieService
                .deleteRefreshTokenCookie();
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .build();
    }

}
