package com.ddrissq.sigdosi.iam.security.securetoken.service;

public interface SecureTokenService {

    String generate();
    String generate(int bytes);

}
