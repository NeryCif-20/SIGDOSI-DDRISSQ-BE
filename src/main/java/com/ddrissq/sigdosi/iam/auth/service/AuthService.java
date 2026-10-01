package com.ddrissq.sigdosi.iam.auth.service;

import com.ddrissq.sigdosi.iam.auth.dto.AuthIdentifyRequest;
import com.ddrissq.sigdosi.iam.auth.dto.AuthLoginRequest;
import com.ddrissq.sigdosi.iam.auth.dto.AuthPasswordValidateRequest;
import com.ddrissq.sigdosi.iam.auth.dto.AuthPasswordSetupRequest;
import com.ddrissq.sigdosi.iam.auth.model.AuthIdentityResult;
import com.ddrissq.sigdosi.iam.auth.model.AuthResult;

public interface AuthService {

    AuthIdentityResult identify(AuthIdentifyRequest request);
    AuthResult login(String token, AuthLoginRequest request);
    AuthResult refresh(String token);
    void sendSetPasswordMail(String token);
    void sendResetPasswordMail(String token);
    void validatePasswordToken(AuthPasswordValidateRequest request);
    AuthResult setupPassword(AuthPasswordSetupRequest request);
    void logout(String token);

}
