package com.ddrissq.sigdosi.iam.auth.passwordtoken.service;

import com.ddrissq.sigdosi.iam.auth.passwordtoken.entity.PasswordToken;
import com.ddrissq.sigdosi.iam.auth.passwordtoken.model.PasswordTokenPurpose;
import com.ddrissq.sigdosi.iam.user.entity.UserAccount;

public interface PasswordTokenService {

    String create(UserAccount user, PasswordTokenPurpose purpose);
    PasswordToken getByTokenOrThrow(String token);
    void deleteAllExpiredTokens();

}
