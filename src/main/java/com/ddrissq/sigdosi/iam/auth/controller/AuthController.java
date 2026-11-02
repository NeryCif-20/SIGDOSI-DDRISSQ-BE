package com.ddrissq.sigdosi.iam.auth.controller;

import com.ddrissq.sigdosi.iam.auth.cookie.model.AuthCookieNames;
import com.ddrissq.sigdosi.iam.auth.cookie.service.AuthCookieService;
import com.ddrissq.sigdosi.iam.auth.dto.*;
import com.ddrissq.sigdosi.iam.auth.model.AuthIdentifyResult;
import com.ddrissq.sigdosi.iam.auth.model.AuthResult;
import com.ddrissq.sigdosi.iam.auth.model.PasswordRequired;
import com.ddrissq.sigdosi.iam.auth.model.PasswordSetupRequired;
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
        AuthIdentifyResult result = service.identify(request);
        ResponseEntity<AuthIdentifyResponse> response = ResponseEntity.status(HttpStatus.OK)
                .body(AuthIdentifyResponse.builder()
                        .nextStep(result.nextStep())
                        .build());
        return switch (result) {
            case PasswordSetupRequired ignored -> response;
            case PasswordRequired ignored -> {
                ResponseCookie flowTokenCookie = cookieService.createFlowTokenCookie(
                        ignored.flowToken(), ignored.expiresAt());
                response.getHeaders().add(HttpHeaders.SET_COOKIE, flowTokenCookie.toString());
                yield response;
            }
        };
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

    @PostMapping(path = "/password/forgot")
    public ResponseEntity<Void> sendForgotPasswordMail(
            @CookieValue(value = AuthCookieNames.FLOW_TOKEN) String token) {
        service.sendForgotPasswordMail(token);
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

    @PostMapping(path = "/password/token/validate")
    public ResponseEntity<Void> validate(
            @RequestBody @Valid AuthPasswordValidateRequest request) {
        service.validatePasswordToken(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PostMapping(path = "/password/setup")
    public ResponseEntity<AuthResponse> setupPassword(
            @RequestBody @Valid AuthPasswordSetupRequest request) {
        AuthResult result = service.setupPassword(request);
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
