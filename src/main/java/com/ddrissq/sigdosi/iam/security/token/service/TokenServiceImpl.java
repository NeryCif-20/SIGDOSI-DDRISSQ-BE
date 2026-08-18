package com.ddrissq.sigdosi.iam.security.token.service;

import com.ddrissq.sigdosi.iam.security.token.model.JwtCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {

    private final JwtEncoder jwtEncoder;
    private final SecureRandom secureRandom;

    @Override
    public String generateJwt(JwtCreateParams request) {
        Instant now = Instant.now();
        Instant exp = now.plus(request.expirationTime());
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .subject(request.subject())
                .issuedAt(now)
                .expiresAt(exp)
                .claims(claims -> claims.putAll(request.claims()))
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet))
                .getTokenValue();
    }

    @Override
    public String generateOpaque() {
        return this.generateOpaque(64);
    }

    @Override
    public String generateOpaque(int size) {
        byte[] bytes = new byte[size];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }

}
