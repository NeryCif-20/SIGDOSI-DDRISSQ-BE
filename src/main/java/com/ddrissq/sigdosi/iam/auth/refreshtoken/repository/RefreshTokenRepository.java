package com.ddrissq.sigdosi.iam.auth.refreshtoken.repository;

import com.ddrissq.sigdosi.iam.auth.refreshtoken.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

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
    void revokeAllByFamilyId(UUID familyId);

    @Modifying
    @Query(value = """
    DELETE FROM RefreshToken rt
    WHERE rt.expiresAt < CURRENT_TIMESTAMP
    """)
    void deleteAllExpired();

}
