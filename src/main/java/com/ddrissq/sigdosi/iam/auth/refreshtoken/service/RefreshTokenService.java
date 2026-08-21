package com.ddrissq.sigdosi.iam.auth.refreshtoken.service;

import com.ddrissq.sigdosi.iam.auth.refreshtoken.model.RefreshToken;
import com.ddrissq.sigdosi.iam.auth.refreshtoken.model.RefreshTokenResult;
import com.ddrissq.sigdosi.iam.user.model.User;

public interface RefreshTokenService {

    RefreshTokenResult create(User user);
    RefreshTokenResult rotate(RefreshToken refreshToken);
    RefreshToken getByTokenOrThrow(String token);
    void deleteAllExpiredTokens();

}
