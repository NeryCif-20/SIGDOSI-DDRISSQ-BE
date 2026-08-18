package com.ddrissq.sigdosi.iam.auth.refreshtoken.repository;

import com.ddrissq.sigdosi.iam.auth.refreshtoken.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByTokenHash(String tokenHash);

    @Modifying
    @Query(value = """
        UPDATE RefreshToken rt
        SET rt.revokedAt = CURRENT_TIMESTAMP
        WHERE rt.familyId = :familyId
            AND rt.revokedAt = NULL
    """)
    void revokeAllByFamilyId(@Param(value = "familyId") UUID familyId);

    void deleteAllByExpiresAtBefore(Instant expiresAtBefore);

}
