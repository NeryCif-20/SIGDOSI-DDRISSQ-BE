package com.ddrissq.sigdosi.iam.auth.flowtoken.service;

import com.ddrissq.sigdosi.iam.auth.configuration.AuthProperties;
import com.ddrissq.sigdosi.iam.auth.flowtoken.model.FlowToken;
import com.ddrissq.sigdosi.iam.auth.flowtoken.model.FlowTokenCreateResult;
import com.ddrissq.sigdosi.iam.auth.flowtoken.repository.FlowTokenRepository;
import com.ddrissq.sigdosi.iam.error.IamErrorDescriptor;
import com.ddrissq.sigdosi.iam.exception.AuthenticationException;
import com.ddrissq.sigdosi.common.hash.service.HashService;
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
    private final HashService hashService;

    @Override
    public FlowTokenCreateResult create(User user) {
        String token = tokenService.generate(32);
        String tokenHash = hashService.digestHex(token,  "SHA-256");
        Instant expiresAt = Instant.now().plus(
                props.flowToken().timeToLive());
        FlowToken flowToken = FlowToken.builder()
                .user(user)
                .tokenHash(tokenHash)
                .expiresAt(expiresAt)
                .build();
        repository.save(flowToken);
        return FlowTokenCreateResult.builder()
                .token(token)
                .expiresAt(expiresAt)
                .build();
    }

    @Override
    public FlowToken getByTokenOrThrow(String token) {
        String tokenHash = hashService.digestHex(token,  "SHA-256");
        return repository
                .findValidByTokenHashAndStep(tokenHash)
                .orElseThrow(() -> new AuthenticationException(
                        IamErrorDescriptor.CREDENTIALS_INVALID));
    }

    @Override
    public void deleteAllExpiredTokens() {
        repository.deleteAllExpired();
    }
}
