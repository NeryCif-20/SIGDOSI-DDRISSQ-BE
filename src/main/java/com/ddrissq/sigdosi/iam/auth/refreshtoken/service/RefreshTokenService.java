package com.ddrissq.sigdosi.iam.auth.refreshtoken.service;

import com.ddrissq.sigdosi.iam.auth.refreshtoken.entity.RefreshToken;
import com.ddrissq.sigdosi.iam.user.entity.UserAccount;

public interface RefreshTokenService {

    String create(UserAccount user);
    String rotate(RefreshToken refreshToken);
    RefreshToken getByTokenOrThrow(String token);
    void deleteAllExpiredTokens();

}
