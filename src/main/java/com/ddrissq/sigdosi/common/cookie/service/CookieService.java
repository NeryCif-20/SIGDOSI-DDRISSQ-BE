package com.ddrissq.sigdosi.common.cookie.service;

import com.ddrissq.sigdosi.common.cookie.configuration.CookieProperties;
import com.ddrissq.sigdosi.configuration.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class CookieService {

    private final CookieProperties props;

    public ResponseCookie create(String name, String value, Duration maxAge) {
        String sameSite = props.sameSite()
                .attributeValue();
        String path = props.path();
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(true)
                .sameSite(sameSite)
                .path(path)
                .maxAge(maxAge)
                .build();
    }

    public ResponseCookie delete(String name) {
        return create(name, null, Duration.ZERO);
    }

}
