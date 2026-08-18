package com.ddrissq.sigdosi.iam.auth.passwordtoken.service;

import com.ddrissq.sigdosi.iam.auth.exception.AuthExceptionMessages;
import com.ddrissq.sigdosi.iam.auth.exception.AuthenticationException;
import com.ddrissq.sigdosi.iam.auth.passwordtoken.entity.PasswordToken;
import com.ddrissq.sigdosi.iam.auth.passwordtoken.model.PasswordTokenPurpose;
import com.ddrissq.sigdosi.iam.auth.passwordtoken.repository.PasswordTokenRepository;
import com.ddrissq.sigdosi.iam.security.configuration.SecurityProperties;
import com.ddrissq.sigdosi.iam.security.token.service.TokenService;
import com.ddrissq.sigdosi.iam.security.util.Hashing;
import com.ddrissq.sigdosi.iam.user.entity.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@RequiredArgsConstructor
@Service
@Transactional
public class PasswordTokenServiceImpl implements PasswordTokenService {

    private final PasswordTokenRepository repository;
    private final TokenService tokenService;
    private final SecurityProperties properties;

    @Override
    public String create(UserAccount user, PasswordTokenPurpose purpose) {
        repository.revokeAllActiveTokensByUser(user);
        String token = tokenService.generateOpaque(32);
        String tokenHash = Hashing.sha256(token);
        Instant expiresAt = Instant.now().plus(
                properties.getPasswordToken().getExpirationTime());
        PasswordToken passwordToken = PasswordToken.builder()
                .user(user)
                .tokenHash(tokenHash)
                .purpose(purpose)
                .expiresAt(expiresAt)
                .build();
        repository.save(passwordToken);
        return token;
    }

    @Override
    public PasswordToken getByTokenOrThrow(String token) {
        String tokenHash = Hashing.sha256(token);
        return repository.findValidToken(tokenHash)
                .orElseThrow(() -> new AuthenticationException(
                        AuthExceptionMessages.BAD_CREDENTIALS));
    }

    @Override
    public void deleteAllExpiredTokens() {
        repository.deleteAllByExpiresAtBefore(Instant.now());
    }

}
