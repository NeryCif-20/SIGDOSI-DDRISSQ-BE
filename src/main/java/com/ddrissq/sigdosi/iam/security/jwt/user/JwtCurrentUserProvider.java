package com.ddrissq.sigdosi.iam.security.jwt.user;

import com.ddrissq.sigdosi.iam.constants.IamErrorMessages;
import com.ddrissq.sigdosi.iam.exception.AuthenticationException;
import com.ddrissq.sigdosi.iam.security.user.CurrentUserProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class JwtCurrentUserProvider implements CurrentUserProvider {

    @Override
    public UUID getUserId() {
        String userId = getJwt().getSubject();
        if (userId == null) {
            throw new AuthenticationException(
                    IamErrorMessages.INVALID_TOKEN);
        }
        return UUID.fromString(userId);
    }

    @Override
    public List<String> getAuthorities() {
        List<String> authorities = getJwt().getClaimAsStringList("authorities");
        if (authorities == null) {
            throw new AuthenticationException(
                    IamErrorMessages.INVALID_TOKEN);
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
                IamErrorMessages.BAD_CREDENTIALS);
    }

}
