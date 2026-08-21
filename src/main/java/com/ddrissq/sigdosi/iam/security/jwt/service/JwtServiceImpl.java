package com.ddrissq.sigdosi.iam.security.jwt.service;

import com.ddrissq.sigdosi.iam.security.jwt.model.JwtGenerateData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class JwtServiceImpl implements JwtService {

    private final JwtEncoder jwtEncoder;

    @Override
    public String generate(JwtGenerateData data) {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(data.timeToLive());
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .subject(data.subject())
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .claims(claims -> claims.putAll(data.claims()))
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet))
                .getTokenValue();
    }

}
