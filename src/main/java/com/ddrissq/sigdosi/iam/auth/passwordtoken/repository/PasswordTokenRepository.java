package com.ddrissq.sigdosi.iam.auth.passwordtoken.repository;

import com.ddrissq.sigdosi.iam.auth.passwordtoken.model.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PasswordTokenRepository extends JpaRepository<PasswordToken, UUID> {

    @Query("""
        SELECT pt
        FROM PasswordToken pt
        WHERE pt.tokenHash = :tokenHash
            AND pt.expiresAt > CURRENT_TIMESTAMP
            AND pt.revokedAt IS NULL
    """)
    Optional<PasswordToken> findValidToken(String tokenHash);

    @Modifying
    @Query("""
        UPDATE PasswordToken pt
        SET pt.revokedAt = CURRENT_TIMESTAMP
        WHERE pt.user.id = :userId
            AND pt.revokedAt IS NULL
            AND pt.expiresAt > CURRENT_TIMESTAMP
    """)
    void revokeAllActiveTokensByUserId(UUID userId);

    @Modifying
    @Query(value = """
    DELETE FROM PasswordToken pt
    WHERE pt.expiresAt < CURRENT_TIMESTAMP
    """)
    void deleteAllExpired();

}
