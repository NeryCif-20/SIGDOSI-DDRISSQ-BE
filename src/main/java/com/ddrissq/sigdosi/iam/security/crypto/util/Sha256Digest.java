package com.ddrissq.sigdosi.iam.security.crypto.util;

import com.ddrissq.sigdosi.iam.security.crypto.constant.DigestErrorMessages;
import com.ddrissq.sigdosi.iam.security.crypto.exception.DigestGenerationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Sha256Digest {

    private static final String ALGORITHM = "SHA-256";

    public static String hash(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException ex) {
            String message = String.format(
                    DigestErrorMessages.NO_SUCH_ALGORITHM, ALGORITHM);
            throw new DigestGenerationException(message);
        }
    }

}
