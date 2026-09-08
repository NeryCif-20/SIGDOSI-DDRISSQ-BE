package com.ddrissq.sigdosi.iam.security.provider;

import com.ddrissq.sigdosi.common.message.service.MessageService;
import com.ddrissq.sigdosi.iam.constants.IamErrorMessageKeys;
import com.ddrissq.sigdosi.iam.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class JwtCurrentUserProviderImpl implements CurrentUserProvider {

    private final MessageService messageService;

    @Override
    public UUID getUserId() {
        String userId = getJwt().getSubject();
        if (userId == null) {
            throw new AuthenticationException(
                    messageService.getMessage(
                            IamErrorMessageKeys.TOKEN_INVALID));
        }
        return UUID.fromString(userId);
    }

    @Override
    public List<String> getAuthorities() {
        List<String> authorities = getJwt().getClaimAsStringList("authorities");
        if (authorities == null) {
            throw new AuthenticationException(
                    messageService.getMessage(
                            IamErrorMessageKeys.TOKEN_INVALID));
        }
        return authorities;
    }

    private Jwt getJwt() {
        Authentication auth = SecurityContextHolder
                .getContext().getAuthentication();
        if (auth instanceof JwtAuthenticationToken jwtAuth) {
            return jwtAuth.getToken();
        }
        throw new AuthenticationException(
                messageService.getMessage(
                        IamErrorMessageKeys.BAD_CREDENTIALS));
    }

}
