package com.ddrissq.sigdosi.iam.auth.service;

import com.ddrissq.sigdosi.iam.auth.configuration.AuthProperties;
import com.ddrissq.sigdosi.iam.auth.dto.AuthIdentifyRequest;
import com.ddrissq.sigdosi.iam.auth.dto.AuthLoginRequest;
import com.ddrissq.sigdosi.iam.auth.dto.AuthPasswordSetRequest;
import com.ddrissq.sigdosi.iam.auth.dto.AuthPasswordValidateRequest;
import com.ddrissq.sigdosi.iam.auth.flowtoken.model.FlowToken;
import com.ddrissq.sigdosi.iam.auth.flowtoken.model.FlowTokenResult;
import com.ddrissq.sigdosi.iam.auth.flowtoken.model.FlowTokenStep;
import com.ddrissq.sigdosi.iam.auth.flowtoken.service.FlowTokenService;
import com.ddrissq.sigdosi.iam.auth.mail.model.PasswordSetEmailData;
import com.ddrissq.sigdosi.iam.auth.mail.service.AuthMailService;
import com.ddrissq.sigdosi.iam.auth.model.AuthIdentityResult;
import com.ddrissq.sigdosi.iam.auth.model.AuthResult;
import com.ddrissq.sigdosi.iam.auth.passwordtoken.model.PasswordToken;
import com.ddrissq.sigdosi.iam.auth.passwordtoken.model.PasswordTokenPurpose;
import com.ddrissq.sigdosi.iam.auth.passwordtoken.model.PasswordTokenResult;
import com.ddrissq.sigdosi.iam.auth.passwordtoken.service.PasswordTokenService;
import com.ddrissq.sigdosi.iam.auth.refreshtoken.model.RefreshToken;
import com.ddrissq.sigdosi.iam.auth.refreshtoken.model.RefreshTokenResult;
import com.ddrissq.sigdosi.iam.auth.refreshtoken.service.RefreshTokenService;
import com.ddrissq.sigdosi.iam.constants.IamErrorMessages;
import com.ddrissq.sigdosi.iam.exception.AuthenticationException;
import com.ddrissq.sigdosi.iam.exception.AuthorizationException;
import com.ddrissq.sigdosi.iam.permission.model.Permission;
import com.ddrissq.sigdosi.iam.security.jwt.model.JwtGenerateData;
import com.ddrissq.sigdosi.iam.security.jwt.service.JwtService;
import com.ddrissq.sigdosi.iam.user.model.User;
import com.ddrissq.sigdosi.iam.user.model.UserStatus;
import com.ddrissq.sigdosi.iam.user.service.UserService;
import lombok.RequiredArgsConstructor;
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
    private final AuthProperties props;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final FlowTokenService flowTokenService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordTokenService passwordTokenService;
    private final AuthMailService mailService;

    @Override
    public AuthIdentityResult identify(AuthIdentifyRequest request) {
        User user = userService.getByEmailOrThrow(request.email());
        FlowTokenStep step = resolveStep(user.getStatus());
        FlowTokenResult result = flowTokenService.create(user, step);
        return AuthIdentityResult.builder()
                .flowToken(result.token())
                .step(result.step())
                .expiresAt(result.expiresAt())
                .build();
    }

    @Override
    public AuthResult login(String token, AuthLoginRequest request) {
        FlowToken flowToken = flowTokenService.getByTokenOrThrow(
                token, FlowTokenStep.PASSWORD);
        User user = flowToken.getUser();
        boolean passwordInvalid = !passwordEncoder.matches(
                request.password(),
                user.getPasswordHash());
        if (passwordInvalid) {
            throw new AuthenticationException(
                    IamErrorMessages.BAD_CREDENTIALS);
        }
        flowToken.setRevokedAt(Instant.now());
        RefreshTokenResult result = refreshTokenService.create(user);
        return AuthResult.builder()
                .accessToken(issueAccessToken(user))
                .refreshToken(result.token())
                .expiresAt(result.expiresAt())
                .build();
    }

    @Override
    public AuthResult refresh(String token) {
        RefreshToken refreshToken = refreshTokenService.getByTokenOrThrow(token);
        RefreshTokenResult result = refreshTokenService.rotate(refreshToken);
        return AuthResult.builder()
                .accessToken(issueAccessToken(refreshToken.getUser()))
                .refreshToken(result.token())
                .expiresAt(result.expiresAt())
                .build();
    }

    @Override
    public void sendSetupPasswordEmail(String token) {
        FlowToken flowToken = flowTokenService.getByTokenOrThrow(
                token, FlowTokenStep.SETUP_PASSWORD);
        User user = flowToken.getUser();
        PasswordTokenResult result = passwordTokenService.create(
                user, PasswordTokenPurpose.SETUP_PASSWORD);
        mailService.sendSetupPasswordEmail(
                buildPasswordSetEmailData(result));
        flowToken.setRevokedAt(Instant.now());
    }

    @Override
    public void sendResetPasswordEmail(String token) {
        FlowToken flowToken = flowTokenService.getByTokenOrThrow(
                token, FlowTokenStep.PASSWORD);
        User user = flowToken.getUser();
        PasswordTokenResult result = passwordTokenService.create(
                user, PasswordTokenPurpose.RESET_PASSWORD);
        mailService.sendResetPasswordEmail(
                buildPasswordSetEmailData(result));
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
        User user = passwordToken.getUser();
        String newPasswordHash = passwordEncoder.encode(request.newPassword());
        user.setPasswordHash(newPasswordHash);
        if (user.getStatus() == UserStatus.PENDING) {
            user.setStatus(UserStatus.ACTIVE);
        }
        passwordToken.setRevokedAt(Instant.now());
        RefreshTokenResult result = refreshTokenService.create(user);
        return AuthResult.builder()
                .accessToken(issueAccessToken(user))
                .refreshToken(result.token())
                .expiresAt(result.expiresAt())
                .build();
    }

    @Override
    public void logout(String token) {
        RefreshToken refreshToken = refreshTokenService.getByTokenOrThrow(token);
        refreshToken.setRevokedAt(Instant.now());
    }

    private FlowTokenStep resolveStep(UserStatus status) {
        return switch (status) {
            case ACTIVE -> FlowTokenStep.PASSWORD;
            case PENDING -> FlowTokenStep.SETUP_PASSWORD;
            default -> throw new AuthorizationException(
                    IamErrorMessages.USER_DISABLED);
        };
    }

    private String issueAccessToken(User user) {
        Duration timeToLive = props.accessToken().timeToLive();
        List<String> authorities = user.getRole().getPermissions().stream()
                .map(Permission::getAuthority)
                .toList();
        JwtGenerateData params = JwtGenerateData.builder()
                .subject(user.getId().toString())
                .timeToLive(timeToLive)
                .claim("authorities", authorities)
                .build();
        return jwtService.generate(params);
    }

    private PasswordSetEmailData buildPasswordSetEmailData(PasswordTokenResult result) {
        return PasswordSetEmailData.builder()
                .to(result.user().getEmail())
                .name(result.user().getProfile().getFullName())
                .token(result.token())
                .expiresAt(result.expiresAt())
                .build();
    }

}
