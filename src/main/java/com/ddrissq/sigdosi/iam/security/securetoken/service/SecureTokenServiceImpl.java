package com.ddrissq.sigdosi.iam.security.securetoken.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@RequiredArgsConstructor
@Service
public class SecureTokenServiceImpl implements SecureTokenService {

    private final SecureRandom secureRandom;

    @Override
    public String generate() {
        return this.generate(64);
    }

    @Override
    public String generate(int size) {
        byte[] bytes = new byte[size];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }

}
