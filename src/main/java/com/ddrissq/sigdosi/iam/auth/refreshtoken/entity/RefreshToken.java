package com.ddrissq.sigdosi.iam.auth.refreshtoken.entity;

import com.ddrissq.sigdosi.iam.user.entity.UserAccount;
import com.ddrissq.sigdosi.shared.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class RefreshToken extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount user;
    private String tokenHash;
    private UUID familyId;
    private Instant revokedAt;
    private Instant expiresAt;

    public boolean isExpired() {
        return this.expiresAt.isBefore(Instant.now());
    }

    public boolean isRevoked() {
        return this.revokedAt != null;
    }

}
