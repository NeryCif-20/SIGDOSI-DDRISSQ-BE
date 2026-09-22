package com.ddrissq.sigdosi.iam.auth.configuration;

import com.ddrissq.sigdosi.iam.auth.flowtoken.service.FlowTokenService;
import com.ddrissq.sigdosi.iam.auth.passwordtoken.service.PasswordTokenService;
import com.ddrissq.sigdosi.iam.auth.refreshtoken.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthCleanupJob {

    private final FlowTokenService flowTokenService;
    private final PasswordTokenService passwordTokenService;
    private final RefreshTokenService refreshTokenService;

    @Scheduled(cron = "${auth.flow-token.cleanup}")
    public void cleanupFlowTokens() {
        flowTokenService.deleteAllExpiredTokens();
    }

    @Scheduled(cron = "${auth.password-token.cleanup}")
    public void cleanupPasswordTokens() {
        passwordTokenService.deleteAllExpiredTokens();
    }

    @Scheduled(cron = "${auth.refresh-token.cleanup}")
    public void cleanupRefreshTokens() {
        refreshTokenService.deleteAllExpiredTokens();
    }

}
