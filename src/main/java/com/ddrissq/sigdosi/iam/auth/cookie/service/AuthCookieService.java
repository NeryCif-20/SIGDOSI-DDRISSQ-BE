package com.ddrissq.sigdosi.iam.auth.cookie.service;

import com.ddrissq.sigdosi.iam.auth.cookie.AuthCookieNames;
import com.ddrissq.sigdosi.iam.security.configuration.SecurityProperties;
import com.ddrissq.sigdosi.shared.cookie.service.CookieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class AuthCookieService {

    private final CookieService service;
    private final SecurityProperties properties;

    public ResponseCookie createRefreshTokenCookie(String token) {
        return service.create(
                AuthCookieNames.REFRESH_TOKEN,
                token,
                properties.getRefreshToken().getExpirationTime()
        );
    }

    public ResponseCookie deleteRefreshTokenCookie() {
        return service.delete(AuthCookieNames.REFRESH_TOKEN);
    }

    public ResponseCookie createFlowTokenCookie(String value) {
        return service.create(
                AuthCookieNames.FLOW_TOKEN,
                value,
                properties.getFlowToken().getExpirationTime()
        );
    }

    public ResponseCookie deleteFlowTokenCookie() {
        return service.delete(AuthCookieNames.FLOW_TOKEN);
    }

}
