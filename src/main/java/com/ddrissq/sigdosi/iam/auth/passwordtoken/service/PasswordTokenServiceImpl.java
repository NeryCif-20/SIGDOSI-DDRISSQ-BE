package com.ddrissq.sigdosi.iam.auth.passwordtoken.service;

import com.ddrissq.sigdosi.iam.auth.configuration.AuthProperties;
import com.ddrissq.sigdosi.iam.auth.passwordtoken.model.PasswordToken;
import com.ddrissq.sigdosi.iam.auth.passwordtoken.model.PasswordTokenPurpose;
import com.ddrissq.sigdosi.iam.auth.passwordtoken.model.PasswordTokenResult;
import com.ddrissq.sigdosi.iam.auth.passwordtoken.repository.PasswordTokenRepository;
import com.ddrissq.sigdosi.iam.constants.IamErrorMessages;
import com.ddrissq.sigdosi.iam.exception.AuthenticationException;
import com.ddrissq.sigdosi.iam.security.crypto.util.Sha256Digest;
import com.ddrissq.sigdosi.iam.security.securetoken.service.SecureTokenService;
import com.ddrissq.sigdosi.iam.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@RequiredArgsConstructor
@Service
@Transactional
public class PasswordTokenServiceImpl implements PasswordTokenService {

    private final PasswordTokenRepository repository;
    private final SecureTokenService tokenService;
    private final AuthProperties props;

    @Override
    public PasswordTokenResult create(User user, PasswordTokenPurpose purpose) {
        repository.revokeAllActiveTokensByUser(user);
        String token = tokenService.generate(32);
        String tokenHash = Sha256Digest.hash(token);
        Instant expiresAt = Instant.now().plus(
                props.passwordToken().timeToLive());
        PasswordToken passwordToken = PasswordToken.builder()
                .user(user)
                .tokenHash(tokenHash)
                .purpose(purpose)
                .expiresAt(expiresAt)
                .build();
        repository.save(passwordToken);
        return PasswordTokenResult.builder()
                .user(user)
                .token(token)
                .purpose(purpose)
                .expiresAt(expiresAt)
                .build();
    }

    @Override
    public PasswordToken getByTokenOrThrow(String token) {
        String tokenHash = Sha256Digest.hash(token);
        return repository.findValidToken(tokenHash)
                .orElseThrow(() -> new AuthenticationException(
                        IamErrorMessages.BAD_CREDENTIALS));
    }

    @Override
    public void deleteAllExpiredTokens() {
        repository.deleteAllByExpiresAtBefore(Instant.now());
    }

}
