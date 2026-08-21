package com.ddrissq.sigdosi.iam.auth.passwordtoken.service;

import com.ddrissq.sigdosi.iam.auth.passwordtoken.model.PasswordToken;
import com.ddrissq.sigdosi.iam.auth.passwordtoken.model.PasswordTokenPurpose;
import com.ddrissq.sigdosi.iam.auth.passwordtoken.model.PasswordTokenResult;
import com.ddrissq.sigdosi.iam.user.model.User;

public interface PasswordTokenService {

    PasswordTokenResult create(User user, PasswordTokenPurpose purpose);
    PasswordToken getByTokenOrThrow(String token);
    void deleteAllExpiredTokens();

}
