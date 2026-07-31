package com.ddrissq.sigdosi.configuration.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

@Getter
@Setter
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    private Token refreshToken;
    private JwtToken accessToken;

    @Getter
    @Setter
    public static class Token {

        private long expirationTime;

    }

    @Getter
    @Setter
    public static class JwtToken extends Token {

        private Resource privateKey;
        private Resource publicKey;

    }

}
