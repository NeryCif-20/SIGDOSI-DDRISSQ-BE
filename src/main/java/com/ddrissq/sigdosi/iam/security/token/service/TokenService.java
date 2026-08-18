package com.ddrissq.sigdosi.iam.security.token.service;

import com.ddrissq.sigdosi.iam.security.token.model.JwtCreateParams;

public interface TokenService {

    String generateJwt(JwtCreateParams params);
    String generateOpaque();
    String generateOpaque(int bytes);

}
