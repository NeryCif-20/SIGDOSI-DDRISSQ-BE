package com.ddrissq.sigdosi.iam.security.hash.service;

import com.ddrissq.sigdosi.common.message.service.MessageService;
import com.ddrissq.sigdosi.iam.security.hash.constant.HashErrorMessageKeys;
import com.ddrissq.sigdosi.iam.security.hash.exception.HashGenerationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@RequiredArgsConstructor
@Service
public final class HashService {

    private final MessageService messageService;

    public String digestHex(String value, String algorithm) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException ex) {
            throw new HashGenerationException(
                    messageService.getMessage(
                            HashErrorMessageKeys.NO_SUCH_ALGORITHM,
                            algorithm));
        }
    }

}
