package com.ddrissq.sigdosi.iam.security.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

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

        private RSAPrivateKey privateKey;
        private RSAPublicKey publicKey;

    }

}
