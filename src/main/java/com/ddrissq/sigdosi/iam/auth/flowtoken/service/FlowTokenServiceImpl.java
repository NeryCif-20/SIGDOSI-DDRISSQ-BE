package com.ddrissq.sigdosi.iam.auth.flowtoken.service;

import com.ddrissq.sigdosi.iam.auth.configuration.AuthProperties;
import com.ddrissq.sigdosi.iam.auth.flowtoken.model.FlowToken;
import com.ddrissq.sigdosi.iam.auth.flowtoken.model.FlowTokenResult;
import com.ddrissq.sigdosi.iam.auth.flowtoken.model.FlowTokenStep;
import com.ddrissq.sigdosi.iam.auth.flowtoken.repository.FlowTokenRepository;
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
public class FlowTokenServiceImpl implements FlowTokenService {

    private final FlowTokenRepository repository;
    private final SecureTokenService tokenService;
    private final AuthProperties props;

    @Override
    public FlowTokenResult create(User user, FlowTokenStep step) {
        String token = tokenService.generate(32);
        String tokenHash = Sha256Digest.hash(token);
        Instant expiresAt = Instant.now().plus(
                props.flowToken().timeToLive());
        FlowToken flowToken = FlowToken.builder()
                .user(user)
                .tokenHash(tokenHash)
                .step(step)
                .expiresAt(expiresAt)
                .build();
        repository.save(flowToken);
        return FlowTokenResult.builder()
                .token(token)
                .step(step)
                .expiresAt(expiresAt)
                .build();
    }

    @Override
    public FlowToken getByTokenOrThrow(String token, FlowTokenStep expectedStep) {
        String tokenHash = Sha256Digest.hash(token);
        return repository
                .findValidToken(tokenHash, expectedStep)
                .orElseThrow(() -> new AuthenticationException(
                        IamErrorMessages.BAD_CREDENTIALS));
    }

    @Override
    public void deleteAllExpiredTokens() {
        repository.deleteAllByExpiresAtBefore(Instant.now());
    }
}
