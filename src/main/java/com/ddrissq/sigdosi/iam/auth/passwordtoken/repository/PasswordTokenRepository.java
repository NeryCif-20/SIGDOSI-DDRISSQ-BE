package com.ddrissq.sigdosi.iam.auth.passwordtoken.repository;

import com.ddrissq.sigdosi.iam.auth.passwordtoken.entity.PasswordToken;
import com.ddrissq.sigdosi.iam.user.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
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
    Optional<PasswordToken> findValidToken(
            @Param("tokenHash") String tokenHash);

    @Modifying
    @Query("""
        UPDATE PasswordToken pt
        SET pt.revokedAt = CURRENT_TIMESTAMP
        WHERE pt.user = :user
            AND pt.revokedAt IS NULL
            AND pt.expiresAt > CURRENT_TIMESTAMP
    """)
    void revokeAllActiveTokensByUser(@Param("user") UserAccount user);

    void deleteAllByExpiresAtBefore(Instant expiresAtBefore);

}
