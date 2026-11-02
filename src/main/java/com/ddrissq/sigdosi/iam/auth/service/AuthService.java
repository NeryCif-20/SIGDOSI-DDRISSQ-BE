package com.ddrissq.sigdosi.iam.auth.service;

import com.ddrissq.sigdosi.iam.auth.dto.AuthIdentifyRequest;
import com.ddrissq.sigdosi.iam.auth.dto.AuthLoginRequest;
import com.ddrissq.sigdosi.iam.auth.dto.AuthPasswordValidateRequest;
import com.ddrissq.sigdosi.iam.auth.dto.AuthPasswordSetupRequest;
import com.ddrissq.sigdosi.iam.auth.model.AuthIdentifyResult;
import com.ddrissq.sigdosi.iam.auth.model.AuthResult;

public interface AuthService {

    AuthIdentifyResult identify(AuthIdentifyRequest request);
    AuthResult login(String token, AuthLoginRequest request);
    AuthResult refresh(String token);
    void sendForgotPasswordMail(String token);
    void validatePasswordToken(AuthPasswordValidateRequest request);
    AuthResult setupPassword(AuthPasswordSetupRequest request);
    void logout(String token);

}
