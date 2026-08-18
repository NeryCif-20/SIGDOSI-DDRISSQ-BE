package com.ddrissq.sigdosi.iam.auth.service;

import com.ddrissq.sigdosi.iam.auth.dto.AuthIdentifyRequest;
import com.ddrissq.sigdosi.iam.auth.dto.AuthLoginRequest;
import com.ddrissq.sigdosi.iam.auth.dto.AuthPasswordValidateRequest;
import com.ddrissq.sigdosi.iam.auth.dto.AuthPasswordSetRequest;
import com.ddrissq.sigdosi.iam.auth.model.AuthIdentityResult;
import com.ddrissq.sigdosi.iam.auth.model.AuthResult;

public interface AuthService {

    AuthIdentityResult identify(AuthIdentifyRequest request);
    AuthResult login(String token, AuthLoginRequest request);
    AuthResult refresh(String token);
    void sendSetupPasswordEmail(String token);
    void sendResetPasswordEmail(String token);
    void validatePasswordToken(AuthPasswordValidateRequest request);
    AuthResult setPassword(AuthPasswordSetRequest request);
    void logout(String token);

}
