package com.ddrissq.sigdosi.common.cookie.service;

import com.ddrissq.sigdosi.configuration.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class CookieService {

    private final ApplicationProperties props;

    public ResponseCookie create(String name, String value, Duration maxAge) {
        String sameSite = props.cookies().sameSite()
                .attributeValue();
        String path = props.cookies().path();
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
