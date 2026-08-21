package com.ddrissq.sigdosi.iam.auth.cookie.service;

import com.ddrissq.sigdosi.common.cookie.service.CookieService;
import com.ddrissq.sigdosi.iam.auth.cookie.constant.AuthCookieNames;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;


@RequiredArgsConstructor
@Service
public class AuthCookieService {

    private final CookieService service;

    public ResponseCookie createRefreshTokenCookie(String token, Instant expiresAt) {
        Duration maxAge = Duration.between(Instant.now(), expiresAt);
        return service.create(
                AuthCookieNames.REFRESH_TOKEN,
                token,
                maxAge
        );
    }

    public ResponseCookie deleteRefreshTokenCookie() {
        return service.delete(AuthCookieNames.REFRESH_TOKEN);
    }

    public ResponseCookie createFlowTokenCookie(String value, Instant expiresAt) {
        Duration maxAge = Duration.between(Instant.now(), expiresAt);
        return service.create(
                AuthCookieNames.FLOW_TOKEN,
                value,
                maxAge
        );
    }

    public ResponseCookie deleteFlowTokenCookie() {
        return service.delete(AuthCookieNames.FLOW_TOKEN);
    }

}
