package com.ddrissq.sigdosi.iam.auth.flowtoken.repository;

import com.ddrissq.sigdosi.iam.auth.flowtoken.model.FlowToken;
import com.ddrissq.sigdosi.iam.auth.flowtoken.model.FlowTokenStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface FlowTokenRepository extends JpaRepository<FlowToken, UUID> {

    @Query("""
        SELECT ft
        FROM FlowToken ft
        WHERE ft.tokenHash = :tokenHash
            AND ft.expiresAt > CURRENT_TIMESTAMP
            AND ft.step = :step
            AND ft.revokedAt IS NULL
    """)
    Optional<FlowToken> findValidToken(String tokenHash, FlowTokenStep step);

    @Modifying
    @Query(value = """
    DELETE FROM FlowToken ft
    WHERE ft.expiresAt < CURRENT_TIMESTAMP
    """)
    void deleteAllExpired();

}
