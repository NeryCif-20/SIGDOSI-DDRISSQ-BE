package com.ddrissq.sigdosi.iam.auth.service;

import com.ddrissq.sigdosi.iam.auth.dto.AuthIdentifyRequest;
import com.ddrissq.sigdosi.iam.auth.dto.AuthLoginRequest;
import com.ddrissq.sigdosi.iam.auth.dto.AuthPasswordValidateRequest;
import com.ddrissq.sigdosi.iam.auth.dto.AuthPasswordSetRequest;
import com.ddrissq.sigdosi.iam.auth.flowtoken.entity.FlowToken;
import com.ddrissq.sigdosi.iam.auth.flowtoken.service.FlowTokenService;
import com.ddrissq.sigdosi.iam.auth.passwordtoken.entity.PasswordToken;
import com.ddrissq.sigdosi.iam.auth.passwordtoken.model.PasswordTokenPurpose;
import com.ddrissq.sigdosi.iam.auth.passwordtoken.service.PasswordTokenService;
import com.ddrissq.sigdosi.iam.auth.refreshtoken.entity.RefreshToken;
import com.ddrissq.sigdosi.iam.auth.exception.AuthExceptionMessages;
import com.ddrissq.sigdosi.iam.auth.exception.AuthenticationException;
import com.ddrissq.sigdosi.iam.auth.exception.AuthorizationException;
import com.ddrissq.sigdosi.iam.auth.model.AuthIdentityResult;
import com.ddrissq.sigdosi.iam.auth.model.AuthResult;
import com.ddrissq.sigdosi.iam.auth.flowtoken.model.FlowStep;
import com.ddrissq.sigdosi.iam.auth.refreshtoken.service.RefreshTokenService;
import com.ddrissq.sigdosi.iam.permission.entity.Permission;
import com.ddrissq.sigdosi.iam.security.configuration.SecurityProperties;
import com.ddrissq.sigdosi.iam.security.token.model.JwtCreateParams;
import com.ddrissq.sigdosi.iam.security.token.service.TokenService;
import com.ddrissq.sigdosi.iam.user.entity.UserAccount;
import com.ddrissq.sigdosi.iam.user.model.UserAccountStatus;
import com.ddrissq.sigdosi.iam.user.service.UserService;
import com.ddrissq.sigdosi.shared.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final SecurityProperties properties;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final FlowTokenService flowTokenService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordTokenService passwordTokenService;
    private final MailService mailService;

    @Override
    public AuthIdentityResult identify(AuthIdentifyRequest request) {
        UserAccount user = userService.getByEmailOrThrow(request.email());
        FlowStep step = resolveStep(user.getStatus());
        String flowToken = flowTokenService.create(user, step);
        return AuthIdentityResult.builder()
                .flowToken(flowToken)
                .step(step)
                .build();
    }

    @Override
    public AuthResult login(String token, AuthLoginRequest request) {
        FlowToken flowToken = flowTokenService.getByTokenOrThrow(
                token, FlowStep.PASSWORD);
        UserAccount user = flowToken.getUser();
        boolean passwordInvalid = !passwordEncoder.matches(
                request.password(),
                user.getPasswordHash());
        if (passwordInvalid) {
            throw new AuthenticationException(
                    AuthExceptionMessages.BAD_CREDENTIALS);
        }
        flowToken.setRevokedAt(Instant.now());
        return AuthResult.builder()
                .accessToken(issueAccessToken(user))
                .refreshToken(refreshTokenService.create(user))
                .build();
    }

    @Override
    public AuthResult refresh(String token) {
        RefreshToken refreshToken = refreshTokenService.getByTokenOrThrow(token);
        UserAccount user = refreshToken.getUser();
        return AuthResult.builder()
                .accessToken(issueAccessToken(user))
                .refreshToken(refreshTokenService.rotate(refreshToken))
                .build();
    }

    @Override
    public void sendSetupPasswordEmail(String token) {
        FlowToken flowToken = flowTokenService.getByTokenOrThrow(
                token, FlowStep.SETUP_PASSWORD);
        UserAccount user = flowToken.getUser();
        sendPasswordEmail(user, PasswordTokenPurpose.SETUP_PASSWORD);
        flowToken.setRevokedAt(Instant.now());
    }

    @Override
    public void sendResetPasswordEmail(String token) {
        FlowToken flowToken = flowTokenService.getByTokenOrThrow(
                token, FlowStep.PASSWORD);
        UserAccount user = flowToken.getUser();
        sendPasswordEmail(user, PasswordTokenPurpose.RESET_PASSWORD);
        flowToken.setRevokedAt(Instant.now());
    }

    @Override
    public void validatePasswordToken(AuthPasswordValidateRequest request) {
        passwordTokenService.getByTokenOrThrow(request.token());
    }

    @Override
    public AuthResult setPassword(AuthPasswordSetRequest request) {
        PasswordToken passwordToken = passwordTokenService
                .getByTokenOrThrow(request.token());
        UserAccount user = passwordToken.getUser();
        String newPasswordHash = passwordEncoder.encode(request.newPassword());
        user.setPasswordHash(newPasswordHash);
        if (user.getStatus() == UserAccountStatus.PENDING) {
            user.setStatus(UserAccountStatus.ACTIVE);
        }
        passwordToken.setRevokedAt(Instant.now());
        return AuthResult.builder()
                .accessToken(issueAccessToken(user))
                .refreshToken(refreshTokenService.create(user))
                .build();
    }

    @Override
    public void logout(String token) {
        RefreshToken refreshToken = refreshTokenService.getByTokenOrThrow(token);
        refreshToken.setRevokedAt(Instant.now());
    }

    private FlowStep resolveStep(UserAccountStatus status) {
        return switch (status) {
            case ACTIVE -> FlowStep.PASSWORD;
            case PENDING -> FlowStep.SETUP_PASSWORD;
            default -> throw new AuthorizationException(
                    AuthExceptionMessages.USER_DISABLED);
        };
    }

    private String issueAccessToken(UserAccount user) {
        Duration exp = properties.getAccessToken().getExpirationTime();
        List<String> authorities = user.getRole().getPermissions().stream()
                .map(Permission::getAuthority)
                .toList();
        JwtCreateParams request = JwtCreateParams.builder()
                .subject(user.getId().toString())
                .expirationTime(exp)
                .claim("authorities", authorities)
                .build();
        return tokenService.generateJwt(request);
    }

    private void sendPasswordEmail(UserAccount user, PasswordTokenPurpose purpose) {
        String to = user.getEmail();
        String name = user.getProfile().getFullName();
        String token = passwordTokenService.create(user, purpose);
        mailService.sendPasswordEmail(to, name, token, purpose);
    }

}
