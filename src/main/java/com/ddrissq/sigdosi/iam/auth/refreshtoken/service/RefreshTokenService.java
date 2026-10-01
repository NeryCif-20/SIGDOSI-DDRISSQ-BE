package com.ddrissq.sigdosi.iam.auth.refreshtoken.service;

import com.ddrissq.sigdosi.iam.auth.refreshtoken.model.RefreshToken;
import com.ddrissq.sigdosi.iam.auth.refreshtoken.model.RefreshTokenIssueResult;
import com.ddrissq.sigdosi.iam.user.model.User;

public interface RefreshTokenService {

    RefreshTokenIssueResult create(User user);
    RefreshTokenIssueResult rotate(RefreshToken refreshToken);
    RefreshToken getByTokenOrThrow(String token);
    void deleteAllExpiredTokens();

}
