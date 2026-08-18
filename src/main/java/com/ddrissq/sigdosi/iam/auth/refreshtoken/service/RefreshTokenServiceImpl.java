package com.ddrissq.sigdosi.iam.auth.refreshtoken.service;

import com.ddrissq.sigdosi.iam.auth.refreshtoken.entity.RefreshToken;
import com.ddrissq.sigdosi.iam.auth.exception.AuthExceptionMessages;
import com.ddrissq.sigdosi.iam.auth.exception.AuthenticationException;
import com.ddrissq.sigdosi.iam.auth.refreshtoken.repository.RefreshTokenRepository;
import com.ddrissq.sigdosi.iam.security.configuration.SecurityProperties;
import com.ddrissq.sigdosi.iam.security.token.service.TokenService;
import com.ddrissq.sigdosi.iam.security.util.Hashing;
import com.ddrissq.sigdosi.iam.user.entity.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final SecurityProperties properties;
    private final TokenService tokenService;

    @Override
    public String create(UserAccount user) {
        return issueRefreshToken(user, null);
    }

    @Override
    public String rotate(RefreshToken refreshToken) {
        refreshToken.setRevokedAt(Instant.now());
        return issueRefreshToken(
                refreshToken.getUser(),
                refreshToken.getFamilyId());
    }

    @Override
    public RefreshToken getByTokenOrThrow(String token) {
        String tokenHash = Hashing.sha256(token);
        RefreshToken refreshToken =  repository.findByTokenHash(tokenHash)
                .orElseThrow(() -> new AuthenticationException(
                        AuthExceptionMessages.BAD_CREDENTIALS));
        if (refreshToken.isRevoked()) {
            repository.revokeAllByFamilyId(refreshToken.getFamilyId());
            throw new AuthenticationException(
                    AuthExceptionMessages.BAD_CREDENTIALS);
        }
        if (refreshToken.isExpired()) {
            throw new AuthenticationException(
                    AuthExceptionMessages.BAD_CREDENTIALS);
        }
        return refreshToken;
    }

    public String issueRefreshToken(UserAccount user, UUID familyId) {
        String token = tokenService.generateOpaque();
        String tokenHash = Hashing.sha256(token);
        Instant expiresAt = Instant.now().plus(
                properties.getRefreshToken().getExpirationTime());
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .tokenHash(tokenHash)
                .expiresAt(expiresAt)
                .familyId(familyId != null
                        ? familyId
                        : UUID.randomUUID())
                .build();
        repository.save(refreshToken);
        return token;
    }

    @Override
    public void deleteAllExpiredTokens() {
        repository.deleteAllByExpiresAtBefore(Instant.now());
    }
}
