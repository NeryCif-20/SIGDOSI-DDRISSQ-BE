package com.ddrissq.sigdosi.iam.auth.passwordtoken.service;

import com.ddrissq.sigdosi.iam.auth.model.PasswordSetupAction;
import com.ddrissq.sigdosi.iam.auth.passwordtoken.model.PasswordToken;
import com.ddrissq.sigdosi.iam.auth.passwordtoken.model.PasswordTokenCreateResult;
import com.ddrissq.sigdosi.iam.user.model.User;

public interface PasswordTokenService {

    PasswordTokenCreateResult create(User user, PasswordSetupAction purpose);
    PasswordToken getByTokenOrThrow(String token);
    void deleteAllExpiredTokens();

}
