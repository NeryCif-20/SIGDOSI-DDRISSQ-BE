package com.ddrissq.sigdosi.iam.security.jwt.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        RSAPrivateKey privateKey,
        RSAPublicKey publicKey
) {
}
