package com.ddrissq.sigdosi.iam.auth.passwordtoken.service;

import com.ddrissq.sigdosi.common.message.service.MessageService;
import com.ddrissq.sigdosi.iam.auth.configuration.AuthProperties;
import com.ddrissq.sigdosi.iam.auth.passwordtoken.model.PasswordToken;
import com.ddrissq.sigdosi.iam.auth.passwordtoken.model.PasswordTokenPurpose;
import com.ddrissq.sigdosi.iam.auth.passwordtoken.model.PasswordTokenResult;
import com.ddrissq.sigdosi.iam.auth.passwordtoken.repository.PasswordTokenRepository;
import com.ddrissq.sigdosi.iam.constants.IamErrorMessageKeys;
import com.ddrissq.sigdosi.iam.exception.AuthenticationException;
import com.ddrissq.sigdosi.iam.security.hash.service.HashService;
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
    private final HashService hashService;
    private final MessageService messageService;

    @Override
    public PasswordTokenResult create(User user, PasswordTokenPurpose purpose) {
        repository.revokeAllActiveTokensByUserId(user.getId());
        String token = tokenService.generate(32);
        String tokenHash = hashService.digestHex(token, "SHA-256");
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
        String tokenHash = hashService.digestHex(token, "SHA-256");
        return repository.findValidToken(tokenHash)
                .orElseThrow(() -> new AuthenticationException(
                        messageService.getMessage(
                                IamErrorMessageKeys.BAD_CREDENTIALS)));
    }

    @Override
    public void deleteAllExpiredTokens() {
        repository.deleteAllExpired();
    }

}
