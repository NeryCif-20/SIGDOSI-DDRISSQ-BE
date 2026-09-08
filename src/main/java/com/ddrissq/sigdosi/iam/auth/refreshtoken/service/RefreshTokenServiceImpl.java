package com.ddrissq.sigdosi.iam.auth.refreshtoken.service;

import com.ddrissq.sigdosi.common.message.service.MessageService;
import com.ddrissq.sigdosi.iam.auth.configuration.AuthProperties;
import com.ddrissq.sigdosi.iam.auth.refreshtoken.model.RefreshToken;
import com.ddrissq.sigdosi.iam.auth.refreshtoken.model.RefreshTokenResult;
import com.ddrissq.sigdosi.iam.auth.refreshtoken.repository.RefreshTokenRepository;
import com.ddrissq.sigdosi.iam.constants.IamErrorMessageKeys;
import com.ddrissq.sigdosi.iam.exception.AuthenticationException;
import com.ddrissq.sigdosi.iam.security.hash.service.HashService;
import com.ddrissq.sigdosi.iam.security.securetoken.service.SecureTokenService;
import com.ddrissq.sigdosi.iam.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final SecureTokenService tokenService;
    private final AuthProperties props;
    private final HashService hashService;
    private final MessageService messageService;

    @Override
    public RefreshTokenResult create(User user) {
        return issueRefreshToken(user, null);
    }

    @Override
    public RefreshTokenResult rotate(RefreshToken refreshToken) {
        refreshToken.setRevokedAt(Instant.now());
        return issueRefreshToken(
                refreshToken.getUser(),
                refreshToken.getFamilyId());
    }

    @Override
    public RefreshToken getByTokenOrThrow(String token) {
        String tokenHash = hashService.digestHex(token, "SHA-256");
        RefreshToken refreshToken =  repository.findByTokenHash(tokenHash)
                .orElseThrow(() -> new AuthenticationException(
                        messageService.getMessage(
                                IamErrorMessageKeys.BAD_CREDENTIALS)));
        if (refreshToken.isRevoked()) {
            repository.revokeAllByFamilyId(refreshToken.getFamilyId());
            throw new AuthenticationException(
                    messageService.getMessage(
                            IamErrorMessageKeys.BAD_CREDENTIALS));
        }
        if (refreshToken.isExpired()) {
            throw new AuthenticationException(
                    messageService.getMessage(
                            IamErrorMessageKeys.BAD_CREDENTIALS));
        }
        return refreshToken;
    }

    @Override
    public void deleteAllExpiredTokens() {
        repository.deleteAllExpired();
    }

    private RefreshTokenResult issueRefreshToken(User user, UUID familyId) {
        String token = tokenService.generate();
        String tokenHash = hashService.digestHex(token, "SHA-256");;
        Instant expiresAt = Instant.now().plus(
                props.refreshToken().timeToLive());
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .tokenHash(tokenHash)
                .expiresAt(expiresAt)
                .familyId(familyId != null
                        ? familyId
                        : UUID.randomUUID())
                .build();
        repository.save(refreshToken);
        return RefreshTokenResult.builder()
                .token(token)
                .expiresAt(expiresAt)
                .build();
    }

}
