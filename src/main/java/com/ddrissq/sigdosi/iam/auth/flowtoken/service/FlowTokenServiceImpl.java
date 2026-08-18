package com.ddrissq.sigdosi.iam.auth.flowtoken.service;

import com.ddrissq.sigdosi.iam.auth.flowtoken.entity.FlowToken;
import com.ddrissq.sigdosi.iam.auth.exception.AuthExceptionMessages;
import com.ddrissq.sigdosi.iam.auth.exception.AuthenticationException;
import com.ddrissq.sigdosi.iam.auth.flowtoken.model.FlowStep;
import com.ddrissq.sigdosi.iam.auth.flowtoken.repository.FlowTokenRepository;
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
public class FlowTokenServiceImpl implements FlowTokenService {

    private final FlowTokenRepository repository;
    private final TokenService tokenService;
    private final SecurityProperties properties;

    @Override
    public String create(UserAccount user, FlowStep step) {
        String token = tokenService.generateOpaque(32);
        String tokenHash = Hashing.sha256(token);
        Instant expiresAt = Instant.now().plus(
                properties.getFlowToken().getExpirationTime());
        FlowToken flowToken = FlowToken.builder()
                .user(user)
                .tokenHash(tokenHash)
                .step(step)
                .expiresAt(expiresAt)
                .build();
        repository.save(flowToken);
        return token;
    }

    @Override
    public FlowToken getByTokenOrThrow(String token, FlowStep expectedStep) {
        String tokenHash = Hashing.sha256(token);
        return repository
                .findValidToken(tokenHash, expectedStep)
                .orElseThrow(() -> new AuthenticationException(
                        AuthExceptionMessages.BAD_CREDENTIALS));
    }

}
